package edu.mriabov.challengertelegrambot.dao.repository;

import edu.mriabov.challengertelegrambot.dao.model.Group;
import edu.mriabov.challengertelegrambot.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query("SELECT u FROM Group g JOIN g.users u WHERE g.telegramId=:groupID AND u.telegramId<>:userID")
    Page<User> findUsersByTelegramID(long userID, long groupID, Pageable pageable);

    @Query("SELECT u FROM Group g JOIN g.users u WHERE g.telegramId=:groupID")
    Set<User> findAllUsersByTelegramID(long groupID);

    boolean existsByTelegramId(long groupID);

    Group findByTelegramId(long groupID);

}
