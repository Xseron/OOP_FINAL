package kz.edu.university.support;

import kz.edu.university.enums.RequestStatus;
import kz.edu.university.user.Employee;
import kz.edu.university.user.TechSupportSpecialist;

import java.io.Serializable;
import java.time.LocalDateTime;

/** support req with status flow */
public class SupportRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee sender;
    private String description;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private TechSupportSpecialist assignee;

    public SupportRequest() {
    }

    public SupportRequest(Employee sender, String description) {
        this.sender = sender;
        this.description = description;
        this.status = RequestStatus.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public Employee getSender() {
        return sender;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TechSupportSpecialist getAssignee() {
        return assignee;
    }

    public void assign(TechSupportSpecialist t) {
        this.assignee = t;
    }

    public void markViewed() {
        // Keep transition monotonic: once moved past NEW, viewing should not roll state back.
        if (status == RequestStatus.NEW)
            status = RequestStatus.VIEWED;
    }

    public void accept() {
        if (status != RequestStatus.VIEWED)
            throw new IllegalStateException("Can only accept a VIEWED request, current: " + status);
        status = RequestStatus.ACCEPTED;
    }

    public void reject() {
        if (status != RequestStatus.VIEWED && status != RequestStatus.ACCEPTED)
            throw new IllegalStateException("Can only reject a VIEWED or ACCEPTED request, current: " + status);
        status = RequestStatus.REJECTED;
    }

    public void markDone() {
        if (status != RequestStatus.ACCEPTED)
            throw new IllegalStateException("Can only mark DONE an ACCEPTED request, current: " + status);
        status = RequestStatus.DONE;
    }

    public boolean isClosed() {
        return status == RequestStatus.DONE || status == RequestStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "Req{" + status + " from " + (sender != null ? sender.getUsername() : "?")
                + ": " + description + "}";
    }
}
