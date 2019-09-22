package se.kth.mauritzz.thesis.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.kth.mauritzz.thesis.batch.Scheduler;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.models.repositories.TransactionRepository;
import se.kth.mauritzz.thesis.tinkapi.provider.rpc.entity.Provider;
import se.kth.mauritzz.thesis.tinkapi.rpc.CreateCredentialRequest;
import se.kth.mauritzz.thesis.tinkapi.rpc.entity.Credential;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage")
@AllArgsConstructor
public class ManageController {

    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    private final TransactionRepository transactionRepository;
    private final ApplicationContext ctx;
    private final JobExplorer jobExplorer;

    @RequestMapping
    public String manage(@AuthenticationPrincipal User user, Model model) {
        final var api = user.getApi();
        final var credentials = api.getCredentials();

        logger.info("{}", credentials);

        model.addAttribute("credentials", credentials);
        model.addAttribute("user", api.getUser());
        return "manage";
    }

    @GetMapping("/add")
    public String listProviders(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("providers", user.getApi().getProviderRepository().findAll());
        return "credentials/providers";
    }

    @GetMapping("/add/{provider}")
    public String addCredential(@PathVariable("provider") String providerName, @AuthenticationPrincipal User user, Model model) {
        Provider provider = user.getApi().getProviderRepository().findByName(providerName).orElseThrow();

        model.addAttribute("providers", provider);
        return "credentials/add";
    }

    @PostMapping("/add/{provider}")
    public String doAddCredential(@PathVariable("provider") String providerName,
                                  @RequestParam Map<String, String> fields,
                                  @AuthenticationPrincipal User user) {
        fields.remove("submit");
        CreateCredentialRequest request = new CreateCredentialRequest(providerName, fields);

        Credential credential = user.getApi().createCredentials(request);

        return "redirect:/manage/poll/" + credential.getId();
    }

    @RequestMapping("/poll/{id}")
    public String poll(@PathVariable String id, @AuthenticationPrincipal User user, Model model) {
        Credential credential = user.getApi().getCredential(id);

        if (credential.getStatus() == Credential.Status.UPDATED)
            return "redirect:/manage/fetch";

        model.addAttribute("credential", credential);
        return "credentials/poll";
    }

    @RequestMapping("/fetch")
    public String fetch(@AuthenticationPrincipal User user) {
        List<Transaction> transactions = user.getApi().getTransactions();
        transactionRepository.saveAll(transactions);

        return "redirect:/manage?saved_tx=" + transactions.size();
    }

    @GetMapping("/batch_trigger")
    @ResponseBody
    public String viewContract(@RequestParam int hour) throws Exception {
        Scheduler scheduler = ctx.getBean(Scheduler.class);
        scheduler.run(hour);

        return "Started successfully";
    }

    @RequestMapping("/batch_list")
    @ResponseBody
    public List<StepExecution> executionList() {
        return jobExplorer.getJobInstances("fetchData", 0, 10)
                .stream()
                .map(jobExplorer::getJobExecutions)
                .flatMap(List::stream)
                .map(JobExecution::getStepExecutions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
