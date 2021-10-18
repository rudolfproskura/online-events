package online.events.util;


import online.events.dto.KorisnikDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.cursor.SearchCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.message.*;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LDAPTest {

    @Test
    public void changeUserGroup() throws Exception {

        KorisnikDto korisnikDto = new KorisnikDto();

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        String postojecaGrupa = null;

        //grupe
        Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
        EntryCursor cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                postojecaGrupa = "admin";
            }
        }

        groupDN = new Dn("cn=organizer,ou=groups,dc=example,dc=com"); //
        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                postojecaGrupa = "organizer";
            }
        }

        groupDN = new Dn("cn=registredUsers,ou=groups,dc=example,dc=com"); //
        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                postojecaGrupa = "registredUsers";
            }
        }




        if (!StringUtils.equals(postojecaGrupa, korisnikDto.getTipKorisnika())) {
            //add to new group

            Modification addUniqueMember = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com");
            connection.modify("cn=" + korisnikDto.getTipKorisnika() + ",ou=groups,dc=example,dc=com", addUniqueMember);

            //remove from current group
            Modification removeUniqueMember = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com");
            connection.modify("cn=" + postojecaGrupa + ",ou=groups,dc=example,dc=com", removeUniqueMember);
        }

        connection.close();



    }

    @Test
    public void testLDAPGetAllUser() throws Exception {

        KorisnikDto korisnikFilterDto = new KorisnikDto();
        korisnikFilterDto.setKorisnickoIme("rudek");


        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");




        //get all users
        ArrayList<KorisnikDto> listaSvihKorisnika = new ArrayList<>();

        Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
        EntryCursor cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            String[] users = entry.get("uniqueMember").toString().split("\\R");
            for (String user : users) {
                KorisnikDto korisnikDto = new KorisnikDto();
                korisnikDto.setKorisnickoIme(StringUtils.substringBetween(user, "uniqueMember: uid=", ",ou=users,dc=example,dc=com"));
                korisnikDto.setKorisnickoImeLDAP(StringUtils.substringAfter(user, "uniqueMember: "));
                korisnikDto.setTipKorisnika("admin");
                listaSvihKorisnika.add(korisnikDto);
            }
        }

        groupDN = new

                Dn("cn=organizer,ou=groups,dc=example,dc=com"); //

        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            String[] users = entry.get("uniqueMember").toString().split("\\R");
            for (String user : users) {
                KorisnikDto korisnikDto = new KorisnikDto();
                korisnikDto.setKorisnickoIme(StringUtils.substringBetween(user, "uniqueMember: uid=", ",ou=users,dc=example,dc=com"));
                korisnikDto.setKorisnickoImeLDAP(StringUtils.substringAfter(user, "uniqueMember: "));
                korisnikDto.setTipKorisnika("organizer");
                listaSvihKorisnika.add(korisnikDto);
            }
        }

        groupDN = new Dn("cn=registredUsers,ou=groups,dc=example,dc=com"); //

        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            String[] users = entry.get("uniqueMember").toString().split("\\R");
            for (String user : users) {
                KorisnikDto korisnikDto = new KorisnikDto();
                korisnikDto.setKorisnickoIme(StringUtils.substringBetween(user, "uniqueMember: uid=", ",ou=users,dc=example,dc=com"));
                korisnikDto.setKorisnickoImeLDAP(StringUtils.substringAfter(user, "uniqueMember: "));
                korisnikDto.setTipKorisnika("registredUsers");
                listaSvihKorisnika.add(korisnikDto);
            }
        }

        for (KorisnikDto korisnikDto : listaSvihKorisnika) {
            groupDN = new Dn(korisnikDto.getKorisnickoImeLDAP()); //nade odredenog usera
            cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                korisnikDto.setIme(entry.get("cn").get().toString());
                korisnikDto.setPrezime(entry.get("sn").get().toString());
                korisnikDto.setEmail(entry.get("mail").get().toString());
                korisnikDto.setOib(entry.get("employeenumber").get().toString());
            }
        }


        //filter list
        List<KorisnikDto> korisnikFilterList = new ArrayList<>();
        if (korisnikFilterDto != null) {
            //korisnicko ime
            if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme())) {
                korisnikFilterList = listaSvihKorisnika.stream()
                        .filter(korisnik ->
                                StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme()))
                        .collect(Collectors.toList());
              //ime
            } else if (StringUtils.isNotBlank(korisnikFilterDto.getIme())) {
                korisnikFilterList = listaSvihKorisnika.stream()
                        .filter(korisnik ->
                                StringUtils.equals(korisnik.getIme(), korisnikFilterDto.getIme()))
                        .collect(Collectors.toList());
            } else if (StringUtils.isNotBlank(korisnikFilterDto.getPrezime())) {
                korisnikFilterList = listaSvihKorisnika.stream()
                        .filter(korisnik ->
                                StringUtils.equals(korisnik.getPrezime(), korisnikFilterDto.getPrezime()))
                        .collect(Collectors.toList());
              //oib
            } else if (StringUtils.isNotBlank(korisnikFilterDto.getOib())) {
            korisnikFilterList = listaSvihKorisnika.stream()
                    .filter(korisnik ->
                            StringUtils.equals(korisnik.getOib(), korisnikFilterDto.getOib()))
                    .collect(Collectors.toList());
             //email
            } else if (StringUtils.isNotBlank(korisnikFilterDto.getEmail())) {
                korisnikFilterList = listaSvihKorisnika.stream()
                        .filter(korisnik ->
                                StringUtils.equals(korisnik.getEmail(), korisnikFilterDto.getEmail()))
                        .collect(Collectors.toList());
                //tip korisnika
            } else if (StringUtils.isNotBlank(korisnikFilterDto.getTipKorisnika())) {
                korisnikFilterList = listaSvihKorisnika.stream()
                        .filter(korisnik ->
                                StringUtils.equals(korisnik.getTipKorisnika(), korisnikFilterDto.getTipKorisnika()))
                        .collect(Collectors.toList());
            } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
                    korisnikFilterList = listaSvihKorisnika;
//              //korisnicko ime i ime
//            } else if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isNotBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                    && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                korisnikFilterList = listaSvihKorisnika.stream()
//                        .filter(korisnik ->
//                                StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme())
//                                && StringUtils.equals(korisnik.getIme(), korisnikFilterDto.getIme()))
//                        .collect(Collectors.toList());
//                //korisnicko ime i prezime
//            } else if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isNotBlank(korisnikFilterDto.getPrezime())
//                    && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                korisnikFilterList = listaSvihKorisnika.stream()
//                        .filter(korisnik ->
//                                StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme())
//                                        && StringUtils.equals(korisnik.getPrezime(), korisnikFilterDto.getPrezime()))
//                        .collect(Collectors.toList());
//
//            } else if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isNotBlank(korisnikFilterDto.getPrezime())
//                    && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                korisnikFilterList = listaSvihKorisnika.stream()
//                        .filter(korisnik ->
//                                StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme())
//                                        && StringUtils.equals(korisnik.getPrezime(), korisnikFilterDto.getPrezime()))
//                        .collect(Collectors.toList());

//                //korisnicko ime
//                if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme()))
//                            .collect(Collectors.toList());
//                    //ime
//                } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isNotBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getIme(), korisnikFilterDto.getIme()))
//                            .collect(Collectors.toList());
//                    //prezime
//                } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isNotBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getPrezime(), korisnikFilterDto.getPrezime()))
//                            .collect(Collectors.toList());
//                    //oib
//                } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isNotBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getOib(), korisnikFilterDto.getOib()))
//                            .collect(Collectors.toList());
//                    //email
//                } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isNotBlank(korisnikFilterDto.getEmail()) && StringUtils.isBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getEmail(), korisnikFilterDto.getEmail()))
//                            .collect(Collectors.toList());
//                    //tip korisnika
//                } else if (StringUtils.isBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isBlank(korisnikFilterDto.getIme()) && StringUtils.isBlank(korisnikFilterDto.getPrezime())
//                        && StringUtils.isBlank(korisnikFilterDto.getOib()) && StringUtils.isBlank(korisnikFilterDto.getEmail()) && StringUtils.isNotBlank(korisnikFilterDto.getTipKorisnika())) {
//                    korisnikFilterList = listaSvihKorisnika.stream()
//                            .filter(korisnik ->
//                                    StringUtils.equals(korisnik.getTipKorisnika(), korisnikFilterDto.getTipKorisnika()))
//                            .collect(Collectors.toList());


//                if (StringUtils.isNotBlank(korisnikFilterDto.getKorisnickoIme()) && StringUtils.isNotBlank(korisnikFilterDto.getIme()) && StringUtils.isNotBlank(korisnikFilterDto.getPrezime())
//                    && StringUtils.isNotBlank(korisnikFilterDto.getOib()) && StringUtils.isNotBlank(korisnikFilterDto.getEmail()) && StringUtils.isNotBlank(korisnikFilterDto.getTipKorisnika())) {
//                korisnikFilterList = listaSvihKorisnika.stream()
//                        .filter(korisnik ->
//                                StringUtils.equals(korisnik.getKorisnickoIme(), korisnikFilterDto.getKorisnickoIme())
//                                && (StringUtils.equals(korisnik.getIme(), korisnikFilterDto.getIme()))
//                                && (StringUtils.equals(korisnik.getPrezime(), korisnikFilterDto.getPrezime()))
//                                && (StringUtils.equals(korisnik.getOib(), korisnikFilterDto.getOib()))
//                                && (StringUtils.equals(korisnik.getEmail(), korisnikFilterDto.getEmail()))
//                                && (StringUtils.equals(korisnik.getTipKorisnika(), korisnikFilterDto.getTipKorisnika())))
//                        .collect(Collectors.toList());
            }

        } else {
            korisnikFilterList = listaSvihKorisnika;
        }


        System.out.println("test");

        cursor.close();

    }


    @Test
    public void testLDAPGetUserGroup() throws Exception {

        KorisnikDto korisnikDto = null;

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        String korisnickoIme = "anica";
        Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
        EntryCursor cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com")) {
                //set admin
                System.out.println(entry);
            }
        }


        cursor.close();


        connection.close();
    }

    @Test
    public void testLDAPGetUserDetail() throws Exception {

        KorisnikDto korisnikDto = null;

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        String korisnickoIme = "natalija";
        Dn userNameDN = new Dn("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search(userNameDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            korisnikDto = new KorisnikDto();
            korisnikDto.setKorisnickoIme(korisnickoIme);
            korisnikDto.setIme(entry.get("cn").get().toString());
            korisnikDto.setPrezime(entry.get("sn").get().toString());
            korisnikDto.setEmail(entry.get("mail").get().toString());
            korisnikDto.setOib(entry.get("employeenumber").get().toString());
            System.out.println(entry);
        }
        //grupe
        Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                korisnikDto.setTipKorisnika("admin");
            }
        }

        groupDN = new Dn("cn=organizer,ou=groups,dc=example,dc=com"); //
        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                korisnikDto.setTipKorisnika("organizer");
            }
        }

        groupDN = new Dn("cn=registredUsers,ou=groups,dc=example,dc=com"); //
        cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

        for (Entry entry : cursor) {
            if (entry.get("uniqueMember").contains("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com")) {
                korisnikDto.setTipKorisnika("registredUsers");
            }
        }

        System.out.println(korisnikDto);

        cursor.close();


        connection.close();
    }


    @Test
    public void testLDAPChangeGroupUser() throws Exception {

        KorisnikDto korisnikDto = new KorisnikDto();
        korisnikDto.setKorisnickoIme("elvis1");
        korisnikDto.setIme("Elvis_new");
        korisnikDto.setPrezime("Elvis_newSure");
        korisnikDto.setEmail("elvis@new.hr");
        korisnikDto.setOib("787878");
        korisnikDto.setLozinka("elvis");
        korisnikDto.setTipKorisnika("organizer");

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        //find
        //   Dn systemDn = new Dn( "dc=example,dc=com" ); //nade sve
        //   Dn systemDn = new Dn( "ou=users,dc=example,dc=com" ); //nade sve usere
        Dn systemDn = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //nade odredenog usera


        EntryCursor cursor = connection.search(systemDn, "(objectclass=*)", SearchScope.OBJECT);

        String postojecaGrupa = "registredUsers";
        String novaGrupa = "admin";

        if (!StringUtils.equals(postojecaGrupa, novaGrupa)) {
            //add to new group

            Modification addUniqueMember = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnikDto.getKorisnickoIme() + "ou=users,dc=example,dc=com");
            connection.modify("cn=" + novaGrupa + ",ou=groups,dc=example,dc=com", addUniqueMember);

            //remove from current group
            Modification removeUniqueMember = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnikDto.getKorisnickoIme() + "ou=users,dc=example,dc=com");
            connection.modify("cn=" + postojecaGrupa + ",ou=groups,dc=example,dc=com", removeUniqueMember);
        }


//        for (Entry entry : cursor) {
//            System.out.println(entry);
//            System.out.println(entry.get("uniquemember").get());
//            System.out.println(entry);
//        }
        cursor.close();

//uid=rudek,ou=users,dc=example,dc=com
//        Modification addUniqueMember = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, "uniqueMember", "uid=apiuser,ou=users,dc=example,dc=com");
//        connection.modify("cn=registredUsers,ou=groups,dc=example,dc=com", addUniqueMember);
//
//
//        Dn systemDn = new Dn(" cn=registredUsers,ou=groups,dc=example,dc=com"); //nade odredenog usera
//        EntryCursor cursor = connection.search(systemDn, "(objectclass=*)", SearchScope.OBJECT);


        for (Entry entry : cursor) {
            System.out.println(entry);
            System.out.println(entry.get("uniquemember").get());
            System.out.println(entry);
        }
        cursor.close();


        connection.close();
    }


    @Test
    public void testLDAPModifyUser() throws Exception {

        KorisnikDto korisnikDto = new KorisnikDto();
        korisnikDto.setKorisnickoIme("elvis1");
        korisnikDto.setIme("Elvis_new");
        korisnikDto.setPrezime("Elvis_newSure");
        korisnikDto.setEmail("elvis@new.hr");
        korisnikDto.setOib("787878");
        korisnikDto.setLozinka("elvis");


        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        //replace cn
        Modification replaceCN = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "cn",
                korisnikDto.getIme());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replaceCN);

        //replace sn
        Modification replaceSN = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "sn",
                korisnikDto.getPrezime());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replaceSN);


        //replace displayName
        Modification replaceDisplayName = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "displayName",
                korisnikDto.getIme() + " " + korisnikDto.getPrezime());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replaceDisplayName);

        //replace displayName
        Modification replaceMail = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "mail",
                korisnikDto.getEmail());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replaceMail);

        //replace OIB
        Modification replaceEN = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "employeenumber",
                korisnikDto.getOib());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replaceEN);

        //replace password
        Modification replacePassword = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "userpassword",
                korisnikDto.getLozinka());
        connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replacePassword);

        connection.close();
    }


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
        Dn systemDn = new Dn(" cn=registredUsers,ou=groups,dc=example,dc=com"); //nade odredenog usera


        String korisnickoIme = "dijana";
        //korisničko ime
        Dn userNameDN = new Dn("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search(userNameDN, "(objectclass=*)", SearchScope.OBJECT);

        //ime


        for (Entry entry : cursor) {


            System.out.println(entry);

            System.out.println(entry);
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
        Dn systemDn = new Dn("uid=apiuser,ou=users,dc=example,dc=com"); //nade odredenog usera


        EntryCursor cursor = connection.search(systemDn, "(objectclass=*)", SearchScope.OBJECT);


        for (Entry entry : cursor) {
            System.out.println(entry);

            System.out.println(entry);
        }
        cursor.close();


        connection.close();
    }

    @Test
    public void testLDAPAddUser() throws Exception {

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");


        Modification addUniqueMember = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, "uniqueMember", "uid=apiuser,ou=users,dc=example,dc=com");
        connection.modify("cn=registredUsers,ou=groups,dc=example,dc=com", addUniqueMember);


        Dn systemDn = new Dn(" cn=registredUsers,ou=groups,dc=example,dc=com"); //nade odredenog usera
        EntryCursor cursor = connection.search(systemDn, "(objectclass=*)", SearchScope.OBJECT);


        for (Entry entry : cursor) {
            System.out.println(entry);

            System.out.println(entry);
        }
        cursor.close();


        connection.close();
    }

    @Test
    public void testSearchWithSearchRequest() throws Exception {

        LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
        connection.setTimeOut(0);
        connection.bind("uid=admin,ou=system", "secret");

        // Create the SearchRequest object
        SearchRequest req = new SearchRequestImpl();
        req.setScope(SearchScope.SUBTREE);
        //req.addAttributes("*");
        req.setTimeLimit(0);
        req.setBase(new Dn("dc=example,dc=com"));
        //req.setFilter("");

        // Process the request
        SearchCursor searchCursor = connection.search(req);

        while (searchCursor.next()) {
            Response response = searchCursor.get();

            // process the SearchResultEntry
            if (response instanceof SearchResultEntry) {
                Entry resultEntry = ((SearchResultEntry) response).getEntry();
                System.out.println(resultEntry);
            }
        }
        connection.close();
    }
}
