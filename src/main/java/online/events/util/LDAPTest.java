package online.events.util;


import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.junit.jupiter.api.Test;


public class LDAPTest {

    @Test
    public void testLDAPConnenction() throws Exception {

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        SearchRequest request = new SearchRequestImpl();

//        request.setScope(SearchScope.SUBTREE);
//        request.setTimeLimit(0);
//        request.setBase(new Dn("dc=example,dc=com"));
//        request.setFilter("(uid=tomica)");



     //   Dn systemDn = new Dn( "dc=example,dc=com" ); //nade sve
     //   Dn systemDn = new Dn( "ou=users,dc=example,dc=com" ); //nade sve usere
/* FIND USER
        Dn systemDn = new Dn( "uid=tea,ou=users,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search( systemDn, "(objectclass=*)", SearchScope.OBJECT );
*/
        Dn systemDn = new Dn( " cn=registredUsers,ou=groups,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search( systemDn, "(objectclass=*)", SearchScope.OBJECT );


        // Process the request
//        SearchCursor searchCursor = connection.search( request );
//
//        while ( searchCursor.next() )
//        {
//            Response response = searchCursor.get();
//
//            // process the SearchResultEntry
//            if ( response instanceof SearchResultEntry)
//            {
//                Entry resultEntry = ( ( SearchResultEntry ) response ).getEntry();
//                System.out.println( resultEntry );
//            }
//        }

        for ( Entry entry : cursor )
        {
            System.out.println( entry );

            System.out.println( entry );
        }
        cursor.close();



        connection.close();
    }

    @Test
    public void testLDAPInsertUser() throws Exception {

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        //form object
        String dn = "uid=" + "eduard" + ",ou=users,dc=example,dc=com";
        String objectClass = "ObjectClass:inetOrgPerson";
        String cn = "cn:" + "Eduard";
        String sn = "sn:" + "Damirovic";
        String displayName = "displayName:" + "Damir" + " " + "Damirovic";
        String mail = "mail:" + "damir@damir.hr";
        String uid = "uid:" + "eduard";
        String employeeNumber = "employeeNumber:" + "777777777";
        String userPassword = "userPassword:" + "eduard";

        //add user
        connection.add(
                new DefaultEntry(
                        "uid=eduard" + ",ou=users,dc=example,dc=com", // The Dn
                        "ObjectClass:inetOrgPerson",
                        cn,
                        sn,
                        displayName,
                        mail,
                        uid,
                        employeeNumber,
                        userPassword
                ));

        SearchRequest request = new SearchRequestImpl();


     /*   LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");


        connection.add(
                new DefaultEntry(
                        "uid=apiuser,ou=users,dc=example,dc=com", // The Dn
                        "ObjectClass: inetOrgPerson",
                       // "ObjectClass: organizationalPerson (structural)",
                       // "ObjectClass: person (structural)",
                      //  "ObjectClass:top (abstract)",
                        "cn: testadd_cn",
                        "sn: testadd_sn",
                        "displayName: tested display Name",
                        "uid: apiuser",
                        "userPassword: apiuserpwd") );

*/




//        request.setScope(SearchScope.SUBTREE);
//        request.setTimeLimit(0);
//        request.setBase(new Dn("dc=example,dc=com"));
//        request.setFilter("(uid=tomica)");

        //   Dn systemDn = new Dn( "dc=example,dc=com" ); //nade sve
        //   Dn systemDn = new Dn( "ou=users,dc=example,dc=com" ); //nade sve usere
        Dn systemDn = new Dn( "uid=apiuser,ou=users,dc=example,dc=com"); //nade odredenog usera



        EntryCursor cursor = connection.search( systemDn, "(objectclass=*)", SearchScope.OBJECT );




        for ( Entry entry : cursor )
        {
            System.out.println( entry );

            System.out.println( entry );
        }
        cursor.close();



        connection.close();
    }

    @Test
    public void testLDAPAddUser() throws Exception {

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");



        Modification addUniqueMember = new DefaultModification( ModificationOperation.ADD_ATTRIBUTE, "uniqueMember", "uid=apiuser,ou=users,dc=example,dc=com");
        connection.modify( "cn=registredUsers,ou=groups,dc=example,dc=com", addUniqueMember );






        Dn systemDn = new Dn( " cn=registredUsers,ou=groups,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search( systemDn, "(objectclass=*)", SearchScope.OBJECT );



        for ( Entry entry : cursor )
        {
            System.out.println( entry );

            System.out.println( entry );
        }
        cursor.close();



        connection.close();
    }
}
