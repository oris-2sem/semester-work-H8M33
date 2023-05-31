package com.example.orisimpl.security.repository;

import com.example.orisimpl.security.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Modifying
    @Query( value = "insert into refresh_token (id, updated_date, account_id, refresh_token) VALUES (:id,now(), :account_id, :refresh_token) on conflict (account_id) do update\n" +
            "set refresh_token = excluded.refresh_token, updated_date = excluded.updated_date;", nativeQuery = true)
    void saveAndUpdate(@Param("id") UUID id,@Param("account_id") UUID account_id, @Param("refresh_token") String refresh_token );

    @Modifying
    @Query(value = "delete from refresh_token where account_id = :account_id ;", nativeQuery = true)
    void deleteByUserId(@Param("account_id") UUID account_id);

    Optional<RefreshTokenEntity> findByUserId(UUID uuid);
}
