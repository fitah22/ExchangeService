package com.s305089.software.exchange.login.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
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
    private String email;
    @NonNull
    private String password;
    private Date lastLogin;
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

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Client)) return false;
        final Client other = (Client) o;
        if (!other.canEqual((Object) this)) return false;
        final Object thisemail = this.getEmail();
        final Object otheremail = other.getEmail();
        if (thisemail == null ? otheremail != null : !thisemail.equals(otheremail)) return false;
        final Object thispassword = this.getPassword();
        final Object otherpassword = other.getPassword();
        if (thispassword == null ? otherpassword != null : !thispassword.equals(otherpassword)) return false;
        final Object thislastLogin = this.getLastLogin();
        final Object otherlastLogin = other.getLastLogin();
        if (thislastLogin == null ? otherlastLogin != null : !thislastLogin.equals(otherlastLogin)) return false;
        final Object thiswallets = this.getAccounts();
        final Object otherwallets = other.getAccounts();
        if (thiswallets == null ? otherwallets != null : !thiswallets.equals(otherwallets)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object email = this.getEmail();
        result = result * PRIME + (email == null ? 43 : email.hashCode());
        final Object password = this.getPassword();
        result = result * PRIME + (password == null ? 43 : password.hashCode());
        final Object lastLogin = this.getLastLogin();
        result = result * PRIME + (lastLogin == null ? 43 : lastLogin.hashCode());
        final Object wallets = this.getAccounts();
        result = result * PRIME + (wallets == null ? 43 : wallets.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Client;
    }

    public String toString() {
        return "Client(email=" + this.getEmail() + ", password=" + this.getPassword() + ", lastLogin=" + this.getLastLogin() + ", accounts=" + this.getAccounts() + ")";
    }
}
