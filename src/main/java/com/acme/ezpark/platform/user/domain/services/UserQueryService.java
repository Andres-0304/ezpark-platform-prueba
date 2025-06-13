package com.acme.ezpark.platform.user.domain.services;

import com.acme.ezpark.platform.user.domain.model.aggregates.User;
import com.acme.ezpark.platform.user.domain.model.queries.GetUserByEmailQuery;
import com.acme.ezpark.platform.user.domain.model.queries.GetUserByIdQuery;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}
