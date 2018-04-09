package com.s305089.software.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    private String address;
    private boolean clamiedReward = false;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    public Client() {
    }


    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<Account> getAccounts() {
        return this.accounts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean hasHasClamiedReward() {
        return clamiedReward;
    }

    public void setHasClamiedReward(boolean clamiedReward) {
        this.clamiedReward = clamiedReward;
    }

    public String toString() {
        return "Client(email=" + this.getEmail() + ", accounts=" + Arrays.deepToString(this.getAccounts().toArray()) + ")";
    }
}
