package edu.mriabov.challengertelegrambot.dialogs.commands;

import edu.mriabov.challengertelegrambot.dialogs.buttons.Buttons;
import edu.mriabov.challengertelegrambot.utils.ReplyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuCommand implements AbilityExtension {

    public Ability onStart(){
        return Ability.builder()
                .name("menu")
                .info("Main menu")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(messageContext -> ReplyUtils.sendMenu(
                        messageContext.update(),messageContext.bot(),Buttons.MAIN_MENU))
                .build();
    }

}
