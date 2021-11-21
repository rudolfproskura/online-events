package online.events.dao;


import online.events.dto.KorisnikDto;
import online.events.exception.DogadajAppRuleException;
import online.events.model.Korisnik;
import org.apache.commons.lang3.StringUtils;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class KorisnikDao extends GenericDao<Object, KorisnikDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int IDX_KORISNIKA_KORISNICKO_IME = 0;
    private static final int IDX_KORISNIK_IME = 1;
    private static final int IDX_KORISNIK_PREZIME = 2;
    private static final int IDX_KORISNIK_OIB = 3;
    private static final int IDX_KORISNIK_EMAIL = 4;
    private static final int IDX_KORISNIK_TIP_KORISNIKA = 5;

    private static final String mailValidator = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+"
            + "(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";

    private static final String oibValidator = "^[0-9]{11}$";

    @Override
    protected Korisnik formEntity(KorisnikDto dto) {
        Korisnik entity = null;
        if (dto != null) {
            entity = new Korisnik();
            entity.setKorisnicko_ime(dto.getKorisnickoIme());
//            entity.setIme(dto.getIme());
//            entity.setPrezime(dto.getPrezime());
//            entity.setOib(dto.getOib());
//            entity.setEmail(dto.getEmail());
//            entity.setTipKorisnika(dto.getTipKorisnika());
        }
        return entity;
    }

    @Override
    protected KorisnikDto formDTO(Object o) {
        KorisnikDto korisnikDto = null;
        if (o != null) {
            korisnikDto = new KorisnikDto();
            if (o instanceof Korisnik) {
                Korisnik korinikEntity = (Korisnik) o;
                korisnikDto.setKorisnickoIme(korinikEntity.getKorisnicko_ime());
//                korisnikDto.setIme(korinikEntity.getIme());
//                korisnikDto.setPrezime(korinikEntity.getPrezime());
//                korisnikDto.setOib(korinikEntity.getOib());
//                korisnikDto.setEmail(korinikEntity.getEmail());
//                korisnikDto.setTipKorisnika(korinikEntity.getTipKorisnika());
            } else {
                Object[] entity = (Object[]) o;
                korisnikDto.setKorisnickoIme((String) entity[IDX_KORISNIKA_KORISNICKO_IME]);
//                korisnikDto.setIme((String) entity[IDX_KORISNIK_IME]);
//                korisnikDto.setPrezime((String) entity[IDX_KORISNIK_PREZIME]);
//                korisnikDto.setOib((String) entity[IDX_KORISNIK_OIB]);
//                korisnikDto.setEmail((String) entity[IDX_KORISNIK_EMAIL]);
//                korisnikDto.setTipKorisnika((String) entity[IDX_KORISNIK_TIP_KORISNIKA]);
            }
        }
        return korisnikDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM Korisnik e";
    }

    public List<KorisnikDto> getFilterList(KorisnikDto filterDto) throws DogadajAppRuleException {
        List<KorisnikDto> resultList;

        //provjera
        if (filterDto == null) {
            throw new DogadajAppRuleException(new ArrayList<>(Arrays.asList("Filter je prazan.")));
        }
        //formiranje sql upita i izvršavanje sql upita
        List<Object[]> listKorisnikObjects = formAndExecuteFilterSql(filterDto);
        //formiranje liste dogadaja
        if (listKorisnikObjects != null && !listKorisnikObjects.isEmpty()) {
            resultList = new ArrayList<>();
            listKorisnikObjects.stream().forEach(p -> resultList.add(formDTO(p)));
        } else {
            resultList = null;
        }
        return resultList;
    }

    public List<KorisnikDto> getFilterListLDAP(KorisnikDto filterDto) throws DogadajAppRuleException {
        List<KorisnikDto> resultList;

        //provjera
        if (filterDto == null) {
            throw new DogadajAppRuleException(new ArrayList<>(Arrays.asList("Filter je prazan.")));
        }
        //formiranje sql upita i izvršavanje sql upita
        List<Object[]> listKorisnikObjects = formAndExecuteFilterSql(filterDto);
        //formiranje liste dogadaja
        if (listKorisnikObjects != null && !listKorisnikObjects.isEmpty()) {
            resultList = new ArrayList<>();
            listKorisnikObjects.stream().forEach(p -> resultList.add(formDTO(p)));
        } else {
            resultList = null;
        }
        return resultList;
    }

    private List<Object[]> formAndExecuteFilterSql(KorisnikDto filterDto) {
        List<Object[]> resultList = null;

        String sql = "select korisnicko_ime from online_events.korisnik ";
        ;
        sql = sql + "where 1 = 1 ";
        //where dio
        if (StringUtils.isNotBlank(filterDto.getKorisnickoIme())) sql = sql + "and korisnicko_ime = :korisnickoIme ";
        if (StringUtils.isNotBlank(filterDto.getIme())) sql = sql + "and ime = :ime ";
        if (StringUtils.isNotBlank(filterDto.getPrezime())) sql = sql + "and prezime = :prezime ";
        if (StringUtils.isNotBlank(filterDto.getOib())) sql = sql + "and oib = :oib ";
        if (StringUtils.isNotBlank(filterDto.getEmail())) sql = sql + "and email = :email ";
        if (StringUtils.isNotBlank(filterDto.getTipKorisnika())) sql = sql + "and tip_korisnika = :tipKorisnika ";

        //default order by
        sql = sql + " order by korisnicko_ime ";
        Query queryDogadaj = getEntityManager().createNativeQuery(sql);
        //parametri
        if (StringUtils.isNotBlank(filterDto.getKorisnickoIme()))
            queryDogadaj.setParameter("korisnickoIme", filterDto.getKorisnickoIme());
        if (StringUtils.isNotBlank(filterDto.getIme()))
            queryDogadaj.setParameter("ime", filterDto.getIme());
        if (StringUtils.isNotBlank(filterDto.getPrezime()))
            queryDogadaj.setParameter("prezime", filterDto.getPrezime());
        if (StringUtils.isNotBlank(filterDto.getOib()))
            queryDogadaj.setParameter("oib", filterDto.getOib());
        if (StringUtils.isNotBlank(filterDto.getEmail()))
            queryDogadaj.setParameter("email", filterDto.getEmail());
        if (StringUtils.isNotBlank(filterDto.getTipKorisnika()))
            queryDogadaj.setParameter("tipKorisnika", filterDto.getTipKorisnika());

        //izvrši query
        resultList = queryDogadaj.getResultList();
        return resultList;
    }

    public void checkUserNameExsists(String inputUsername) throws DogadajAppRuleException {
        if (checkUserNamesExists(inputUsername)) {
            throw new DogadajAppRuleException(Arrays.asList("Korisničko ime " + inputUsername + " već se koristi!"));
        }
    }

    private Boolean checkUserNamesExists(String inputUsername) {
        Boolean exists = Boolean.FALSE;

        String sql = "select korisnicko_ime from online_events.korisnik where korisnicko_ime = :korisnickoIme ";
        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("korisnickoIme", inputUsername);

        List<Object> resultList = query.getResultList();

        if (resultList != null && !resultList.isEmpty()) {
            exists = Boolean.TRUE;
        }

        return exists;
    }

    /**
     * Create new dogadaj entity
     *
     * @param dto
     * @return
     * @throws DogadajAppRuleException
     */
    public KorisnikDto create(KorisnikDto dto) throws DogadajAppRuleException {
        validateBeforeCreate(dto);
        Korisnik entity = formEntity(dto);
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return formDTO(entity);
    }

    /**
     * Edit existing korisnik entity
     *
     * @param korisnikDto
     * @throws DogadajAppRuleException
     */
    public void edit(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        validateBeforeCreate(korisnikDto);
        Korisnik entity = formEntity(korisnikDto);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void validateBeforeCreate(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        boolean hasError = false;
        List<String> messages = new ArrayList<String>();

        if (korisnikDto == null) {
            hasError = true;
            messages.add("Korisnik nema podatke!");
        }
        if (StringUtils.isBlank(korisnikDto.getKorisnickoIme())) {
            hasError = true;
            messages.add("Korisničko ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && (korisnikDto.getKorisnickoIme().length() < 3 ||
                korisnikDto.getKorisnickoIme().length() > 20)) {
            hasError = true;
            messages.add("Korisničko ime mora imati minimalno 3 znaka, a maksimalno 20 znakova!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && korisnikDto.getKorisnickoIme().contains(" ")) {
            hasError = true;
            messages.add("Korisničko ime ne smije sadržavati razmak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && checkUserNamesExists(korisnikDto.getKorisnickoIme())) {
            hasError = true;
            messages.add("Korisničko ime " + korisnikDto.getKorisnickoIme() + " već se koristi!");
        }
        if (StringUtils.isBlank(korisnikDto.getIme())) {
            hasError = true;
            messages.add("Ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getIme()) && (korisnikDto.getIme().length() < 2 ||
                korisnikDto.getIme().length() > 50)) {
            hasError = true;
            messages.add("Ime mora imati minimalno 2 znaka, a maksimalno 50 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getPrezime())) {
            hasError = true;
            messages.add("Prezime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getPrezime()) && (korisnikDto.getPrezime().length() < 2 ||
                korisnikDto.getPrezime().length() > 50)) {
            hasError = true;
            messages.add("Prezime mora imati minimalno 2 znaka, a maksimalno 50 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getOib())) {
            hasError = true;
            messages.add("OIB je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getOib()) && (!korisnikDto.getOib().matches(oibValidator) || korisnikDto.getOib().length() != 11)) {
            hasError = true;
            messages.add("Neispravan OIB, mora sadržavati 11 brojeva!");
        }
        if (StringUtils.isBlank(korisnikDto.getEmail())) {
            hasError = true;
            messages.add("Email je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getEmail()) && !korisnikDto.getEmail().matches(mailValidator)) {
            hasError = true;
            messages.add("Neispravan email!");
        }
        if (StringUtils.isBlank(korisnikDto.getTipKorisnika())) {
            hasError = true;
            messages.add("Korisnik nema popunjenu ulogu!");
        }
        if (StringUtils.isBlank(korisnikDto.getLozinka())) {
            hasError = true;
            messages.add("Lozinka je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getLozinka()) && (korisnikDto.getLozinka().length() < 5 ||
                korisnikDto.getKorisnickoIme().length() > 20)) {
            hasError = true;
            messages.add("Lozinka mora imati minimalno 5 znakova, a maksimalno 20 znakova!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getLozinka()) && korisnikDto.getLozinka().contains(" ")) {
            hasError = true;
            messages.add("Lozinka ne smije sadržavati razmak!");
        }

        if (hasError && !messages.isEmpty()) {
            throw new DogadajAppRuleException(messages);
        }
    }

    public void validateBeforeEditLDAPUser(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        boolean hasError = false;
        List<String> messages = new ArrayList<String>();

        if (korisnikDto == null) {
            hasError = true;
            messages.add("Korisnik nema podatke!");
        }
        if (StringUtils.isBlank(korisnikDto.getKorisnickoIme())) {
            hasError = true;
            messages.add("Korisničko ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && (korisnikDto.getKorisnickoIme().length() < 3 ||
                korisnikDto.getKorisnickoIme().length() > 20)) {
            hasError = true;
            messages.add("Korisničko ime mora imati minimalno 3 znaka, a maksimalno 20 znakova!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && korisnikDto.getKorisnickoIme().contains(" ")) {
            hasError = true;
            messages.add("Korisničko ime ne smije sadržavati razmak!");
        }
//        if (StringUtils.isNotBlank(korisnikDto.getKorisnickoIme()) && checkUserNamesExists(korisnikDto.getKorisnickoIme())) {
//            hasError = true;
//            messages.add("Korisničko ime " + korisnikDto.getKorisnickoIme() + " već se koristi!");
//        }
        if (StringUtils.isBlank(korisnikDto.getIme())) {
            hasError = true;
            messages.add("Ime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getIme()) && (korisnikDto.getIme().length() < 2 ||
                korisnikDto.getIme().length() > 50)) {
            hasError = true;
            messages.add("Ime mora imati minimalno 2 znaka, a maksimalno 50 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getPrezime())) {
            hasError = true;
            messages.add("Prezime je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getPrezime()) && (korisnikDto.getPrezime().length() < 2 ||
                korisnikDto.getPrezime().length() > 50)) {
            hasError = true;
            messages.add("Prezime mora imati minimalno 2 znaka, a maksimalno 50 znakova!");
        }
        if (StringUtils.isBlank(korisnikDto.getOib())) {
            hasError = true;
            messages.add("OIB je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getOib()) && (!korisnikDto.getOib().matches(oibValidator) || korisnikDto.getOib().length() != 11)) {
            hasError = true;
            messages.add("Neispravan OIB, mora sadržavati 11 brojeva!");
        }
        if (StringUtils.isBlank(korisnikDto.getEmail())) {
            hasError = true;
            messages.add("Email je obavezan podatak!");
        }
        if (StringUtils.isNotBlank(korisnikDto.getEmail()) && !korisnikDto.getEmail().matches(mailValidator)) {
            hasError = true;
            messages.add("Neispravan email!");
        }
//        if (StringUtils.isBlank(korisnikDto.getTipKorisnika())) {
//            hasError = true;
//            messages.add("Korisnik nema popunjenu ulogu!");
//        }
//        if (StringUtils.isBlank(korisnikDto.getLozinka())) {
//            hasError = true;
//            messages.add("Lozinka je obavezan podatak!");
//        }
//        if (StringUtils.isNotBlank(korisnikDto.getLozinka()) && (korisnikDto.getLozinka().length() < 5 ||
//                korisnikDto.getKorisnickoIme().length() > 20)) {
//            hasError = true;
//            messages.add("Lozinka mora imati minimalno 5 znakova, a maksimalno 20 znakova!");
//        }
//        if (StringUtils.isNotBlank(korisnikDto.getLozinka()) && korisnikDto.getLozinka().contains(" ")) {
//            hasError = true;
//            messages.add("Lozinka ne smije sadržavati razmak!");
//        }

        if (hasError && !messages.isEmpty()) {
            throw new DogadajAppRuleException(messages);
        }
    }

    public void insertLDAPUser(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        try {

            LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
            connection.setTimeOut(0);
            connection.bind("uid=admin,ou=system", "secret");

            //form object
            String dn = "uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com";
            String objectClass = "ObjectClass:inetOrgPerson";
            String cn = "cn:" + korisnikDto.getIme();
            String sn = "sn:" + korisnikDto.getPrezime();
            String displayName = "displayName:" + korisnikDto.getIme() + " " + korisnikDto.getPrezime();
            String mail = "mail:" + korisnikDto.getEmail();
            String uid = "uid:" + korisnikDto.getKorisnickoIme();
            String userPassword = "userPassword:" + korisnikDto.getLozinka();
            String employeeNumber = "employeeNumber:" + korisnikDto.getOib();

            //add user
            connection.add(
                    new DefaultEntry(
                            "uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", // The Dn
                            "ObjectClass:inetOrgPerson",
                            cn,
                            sn,
                            displayName,
                            mail,
                            uid,
                            employeeNumber,
                            userPassword
                    ));

            //add user in group
            Modification addUniqueMember = new DefaultModification(ModificationOperation.ADD_ATTRIBUTE, "uniqueMember", "uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com");
            connection.modify("cn=registredUsers,ou=groups,dc=example,dc=com", addUniqueMember);

            //close connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom registracije korisnika " + e.getMessage()));
        }
    }

    public void modifyLDAPUser(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        try {
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
//            Modification replacePassword = new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, "userpassword",
//                    korisnikDto.getLozinka());
//            connection.modify("uid=" + korisnikDto.getKorisnickoIme() + ",ou=users,dc=example,dc=com", replacePassword);

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promejen korisničkih podataka " + e.getMessage()));
        }
    }

    public void changeLDAPUserGroup(KorisnikDto korisnikDto) throws DogadajAppRuleException {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promjene uloge korisnika " + e.getMessage()));
        }
    }

    public KorisnikDto getKorisnikInfo(String userName) throws DogadajAppRuleException {
        KorisnikDto korisnikDto = null;

        try {

            LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
            connection.setTimeOut(0);
            connection.bind("uid=admin,ou=system", "secret");

            Dn userNameDN = new Dn("uid=" + userName + ",ou=users,dc=example,dc=com"); //nade odredenog usera
            EntryCursor cursor = connection.search(userNameDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                korisnikDto = new KorisnikDto();
                korisnikDto.setKorisnickoIme(userName);
                korisnikDto.setIme(entry.get("cn").get().toString());
                korisnikDto.setPrezime(entry.get("sn").get().toString());
                korisnikDto.setEmail(entry.get("mail").get().toString());
                korisnikDto.setOib(entry.get("employeenumber").get().toString());
                korisnikDto.setLozinka((entry.get("userpassword").get().toString()));
                //    System.out.println(entry);
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

            cursor.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promjene uloge korisnika " + e.getMessage()));
        }
        return korisnikDto;
    }

    public List<KorisnikDto> getFilterUsers(KorisnikDto korisnikFilterDto) throws DogadajAppRuleException {
        List<KorisnikDto> korisnikFilterList = new ArrayList<>();

        try {
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
                }

            } else {
                korisnikFilterList = listaSvihKorisnika;
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promjene uloge korisnika " + e.getMessage()));
        }

        return korisnikFilterList;

    }

    public void deleteKorisnik(String korisnik) throws DogadajAppRuleException {
        String sql = "delete from online_events.korisnik where korisnicko_ime = :korisnik";

        Query query = getEntityManager().createNativeQuery(sql);
        query.setParameter("korisnik", korisnik);
        query.executeUpdate();
    }

    public void deleteLDAPuser(String korisnik) throws DogadajAppRuleException {
        try {
            LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
            connection.setTimeOut(0);
            connection.bind("uid=admin,ou=system", "secret");

            String postojecaGrupa = null;

            //grupe
            Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
            EntryCursor cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnik + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "admin";
                }
            }

            groupDN = new Dn("cn=organizer,ou=groups,dc=example,dc=com"); //
            cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnik + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "organizer";
                }
            }

            groupDN = new Dn("cn=registredUsers,ou=groups,dc=example,dc=com"); //
            cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnik + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "registredUsers";
                }
            }

            //remove from current group
            Modification removeUniqueMember = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnik + ",ou=users,dc=example,dc=com");
            connection.modify("cn=" + postojecaGrupa + ",ou=groups,dc=example,dc=com", removeUniqueMember);

            //


            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promjene uloge korisnika " + e.getMessage()));
        }
    }

    public void deleteUserFromLDAP(String korisnickoIme) throws DogadajAppRuleException {

        try {

            LdapConnection connection = new LdapNetworkConnection("localhost", 10389);
            connection.setTimeOut(0);
            connection.bind("uid=admin,ou=system", "secret");

            String postojecaGrupa = null;

            //grupe
            Dn groupDN = new Dn("cn=admin,ou=groups,dc=example,dc=com"); //
            EntryCursor cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "admin";
                }
            }

            groupDN = new Dn("cn=organizer,ou=groups,dc=example,dc=com"); //
            cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "organizer";
                }
            }

            groupDN = new Dn("cn=registredUsers,ou=groups,dc=example,dc=com"); //
            cursor = connection.search(groupDN, "(objectclass=*)", SearchScope.OBJECT);

            for (Entry entry : cursor) {
                if (entry.get("uniqueMember").contains("uid=" + korisnickoIme + ",ou=users,dc=example,dc=com")) {
                    postojecaGrupa = "registredUsers";
                }
            }

            //remove from current group
            Modification removeUniqueMember = new DefaultModification(ModificationOperation.REMOVE_ATTRIBUTE,
                    "uniqueMember", "uid=" + korisnickoIme + ",ou=users,dc=example,dc=com");
            connection.modify("cn=" + postojecaGrupa + ",ou=groups,dc=example,dc=com", removeUniqueMember);


            connection.delete( "uid=" + korisnickoIme + ",ou=users,dc=example,dc=com" );
            //connection.delete( "uid=admir,ou=users,dc=example,dc=com" )

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DogadajAppRuleException(Arrays.asList("Dogodila se greška prilikom promjene bisanja korisnika iz LDAP-a." + e.getMessage()));
        }

    }


}
