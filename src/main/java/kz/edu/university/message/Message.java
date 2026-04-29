package kz.edu.university.message;

import kz.edu.university.user.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

/** msg between employees */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private Employee sender;
    private Employee receiver;
    private String text;
    private LocalDateTime sentAt;
    private boolean read;

    public Message() {
    }

    public Message(Employee sender, Employee receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = LocalDateTime.now();
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    @Override
    public String toString() {
        return "Msg{" + sender.getUsername() + " -> " + receiver.getUsername() + ": " + text + "}";
    }
}
