package nl.loxia.raildocs.raildocs;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Tiemen on 11-6-2014.
 */
@SharedPref
public interface PasswordPersister {
    String username();
    String password();
}
