package com.shopify_image_repository;

import com.shopify_image_repository.image_repos.models.Image;
import com.shopify_image_repository.image_repos.models.Role;
import com.shopify_image_repository.image_repos.models.User;
import com.shopify_image_repository.image_repos.models.UserRoles;
import com.shopify_image_repository.image_repos.services.ImageServices;
import com.shopify_image_repository.image_repos.services.RoleServices;
import com.shopify_image_repository.image_repos.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData
        implements CommandLineRunner
{
    @Autowired
    private UserServices useServ;

    @Autowired
    private ImageServices imgServ;

    @Autowired
    private RoleServices roleServ;

    @Transactional
    @Override
    public void run(String[] args) throws Exception {

        //#region Reset Seed
        useServ.deleteAll();
        imgServ.deleteAllImages();
        roleServ.deleteAll();
        //#endregion

        //#region Roles
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        r1 = roleServ.save(r1);
        r2 = roleServ.save(r2);
        //#endregion

        //#region Images
        Image i1 = new Image("A Placid Lake Beneath the Alps", "https://ipt.imgix.net/205438/x/0/11-tips-for-amazing-autumn-photography-8.jpg", false);
        Image i2 = new Image("Kingfisher Kissing the Alps", "https://static.boredpanda.com/blog/wp-content/uploads/2015/11/perfect-kingfisher-dive-photo-wildlife-photography-alan-mcfayden-311.jpg", false);
        Image i3 = new Image("Buddhist Amitayus Mandala", "https://library.acropolis.org/wp-content/uploads/2015/02/910px-Amitayus_Mandala.jpeg", false);
        Image i4 = new Image("An Enthusiastic Chap Smiles into the Aruban Sun", "https://www.flickr.com/photos/191776651@N05/50844032261/in/dateposted-public/", false);
        Image i5 = new Image("Shopify is the Best", "https://jordiob.com/amazon-tools/wp-content/uploads/2020/08/shopify-apps.png", true);
        //#endregion

        //#region Seed user creation
        User admin = new User("admin", "Shopify4Ever", null);
        admin.getRoles().add(new UserRoles(admin, r1));
        admin = useServ.save(admin);

        User user1 = new User("Lebron James", "", "l.j@lalakers.com");
        user1.getRoles().add(new UserRoles(user1, r2));
        user1.getImages().add(i1);
        user1.getImages().add(i2);
        user1.getImages().add(i3);
        user1 = useServ.save(user1);

        User user2 = new User("Alex Goncalves", "", "schroedergoncalves@gmail.com");
        user2.getRoles().add(new UserRoles(user2, r2));
        user2.getImages().add(i4);
        user2.getImages().add(i5);
        user2 = useServ.save(user2);
        //#endregion

    }
}
