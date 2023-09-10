package eduardo.picPaySimplificado.services;

import eduardo.picPaySimplificado.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService  {

    public void sendNotification(User user, String message) {

        String email = user.getEmail();

        System.out.println(email + message);
    }

}
