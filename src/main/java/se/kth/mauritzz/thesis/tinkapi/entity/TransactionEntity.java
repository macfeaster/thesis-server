package se.kth.mauritzz.thesis.tinkapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import se.kth.mauritzz.thesis.annotations.JsonObject;
import se.kth.mauritzz.thesis.models.entities.Transaction;
import se.kth.mauritzz.thesis.util.DateUtils;

import java.time.LocalDateTime;

@JsonObject
@Data
public class TransactionEntity {
    private String accountId;
    private double amount;
    private String categoryId;
    private String categoryType;
    private String credentialsId;
    private long date;
    private String description;
    private String formattedDescription;
    private String id;
    private long inserted;
    private long lastModified;
    private double originalAmount;
    private long originalDate;
    private String originalDescription;
    private boolean pending;
    private long timestamp;
    private String type;
    private String userId;
    private boolean upcoming;
    private double dispensableAmount;
    private boolean userModified;

    @JsonIgnore
    public Transaction toPersistence() {
        return Transaction.builder()
                .tinkId(id)
                .accountId(accountId)
                .amount(originalAmount)
                .description(originalDescription)
                .cleanedDescription(formattedDescription)
                .time(DateUtils.getDateTime(originalDate))
                .pending(pending)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
