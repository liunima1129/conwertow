package com.convertow.definition;

import javax.jcr.RepositoryException;

/**
 * Created by Miroslav on 20.12.2017.
 */
public interface UserLinksResolver {
    boolean useForCurrentPage() throws RepositoryException;

    String getUsername();

    String getProfilePageLink() throws RepositoryException;

    String getRegistrationPageLink() throws RepositoryException;

    String getLoginPageLink() throws RepositoryException;

    String getLogoutLink() throws RepositoryException;
}
