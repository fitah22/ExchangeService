package com.s305089.software.login.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique=true)
    private String email;
    @NonNull
    private String password;
    private String address;
    private Date lastLogin;
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

    public Date getLastLogin() {
        return this.lastLogin;
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

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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
        return "Client(email=" + this.getEmail() + ", lastLogin=" + this.getLastLogin() + ", accounts=" + Arrays.deepToString(this.getAccounts().toArray()) + ")";
    }
}
