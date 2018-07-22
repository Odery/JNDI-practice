package com.vakamisu.jndi.naming;

import com.sun.jndi.fscontext.RefFSContext;

import javax.naming.*;
import java.util.Hashtable;

public class FSNamingServiceJNDI {

    public static void main(String[] args) throws NamingException {

        Context context = null;

        try {
            // Create the initial context.  The environment
            // information specifies the JNDI provider to use
            // and the initial URL to use (in our case, a
            // directory in URL form -- file:///...).
            Hashtable<String, String> hashtableEnvironment = new Hashtable<>();

            hashtableEnvironment.put(
                    Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.fscontext.RefFSContextFactory"
            );

            hashtableEnvironment.put(
                    Context.PROVIDER_URL,
                    args[0]
            );

            context = new InitialContext(hashtableEnvironment);

            // If you provide no other command line arguments,
            // list all of the names in the specified context and
            // the objects they are bound to.
            if (args.length == 1) {
                listFiles(context);
            }

            // Otherwise, list the names and bindings for the
            // specified arguments.
            else {
                for (int i = 1; i < args.length; i++) {
                    Object object = context.lookup(args[i]);
                    System.out.println(
                            args[i] + " " +
                                    object
                    );
                }
            }
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }

    private static void listFiles(Context context) throws NamingException {
        NamingEnumeration namingenumeration = context.listBindings("");
        while (namingenumeration.hasMore()) {
            Binding binding = (Binding)namingenumeration.next();
            System.out.println(
                    binding.getName() + " " +
                            binding.getObject()
            );

            //Checking if current obj is sub-context (dir)
            //if true, show it bindings recursively
            if (binding.getObject() instanceof RefFSContext) {
                System.out.printf("---------------------------------DIR %s BELOW---------------------------------\n\n",binding.getName());
                listFiles((Context) binding.getObject());
                System.out.printf("---------------------------------END OF %s------------------------------------\n",binding.getName());
            }
        }
    }
}