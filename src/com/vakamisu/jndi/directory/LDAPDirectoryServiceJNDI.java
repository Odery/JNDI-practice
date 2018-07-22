package com.vakamisu.jndi.directory;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LDAPDirectoryServiceJNDI {

    public static void main(String[] args) throws NamingException {

        DirContext context = null;

        try {

            Hashtable<String, String> hashtableEnvironment = new Hashtable<>();

            //defining context factory object
            hashtableEnvironment.put(
                    Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory"
            );

            //providing url to LDAP service in my case
            //its link to ApacheDS
            hashtableEnvironment.put(
                    Context.PROVIDER_URL,
                    "ldap://localhost:10389/uid=admin,ou=system"
            );

            //optional; providing user id to service
            hashtableEnvironment.put(
                    Context.SECURITY_PRINCIPAL,
                    "uid=admin,ou=system"
            );

            //optional; providing password to service
            hashtableEnvironment.put(
                    Context.SECURITY_CREDENTIALS,
                    "secret"
            );

            //optional; setting authentication method
            //to simple
            hashtableEnvironment.put(
                    Context.SECURITY_AUTHENTICATION,
                    "simple"
            );

            //Creating DirContext obj
            context = new InitialDirContext(hashtableEnvironment);

            //context.bind("cn=foo","Hello World");

            System.out.println(context.lookup("cn=foo"));

        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

}
