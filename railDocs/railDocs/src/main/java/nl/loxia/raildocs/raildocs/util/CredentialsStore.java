package nl.loxia.raildocs.raildocs.util;

import org.androidannotations.annotations.EBean;

import nl.loxia.raildocs.raildocs.IRailCloud;

/**
 * Created by Tiemen on 12-6-2014.
 */
@EBean(scope = EBean.Scope.Singleton)
public class CredentialsStore {
    public String username;
    public String password;

    public void setCredentials(IRailCloud railCloud) {
        railCloud.setHttpBasicAuth(username, password);
    }
}
