package com.eazybytes.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyCloakRoleConverter  implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        //reading the realm_access value from jwt token
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        //check the realms roles null or simply empty then return empty collection
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        //in case if realm roles exists, read the list roles as strings..
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)// adding "ROLE_" prefix to the roles ex: for ACCOUNTS role, it will be ROLE_ACCOUNTS
                .map(SimpleGrantedAuthority::new) // it will be mapping as simplegranted authority.
                .collect(Collectors.toList()); // taking care of converting all the simpleauthority objects to a collection.
        return returnValue;
    }

}