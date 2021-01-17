package com.shopify_image_repository;

import com.shopify_image_repository.image_repos.models.*;
import com.shopify_image_repository.image_repos.services.ImageServices;
import com.shopify_image_repository.image_repos.services.RoleServices;
import com.shopify_image_repository.image_repos.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws Exception {

        //#region Reset Seed
        useServ.deleteAll();
        imgServ.deleteAllImages();
        roleServ.deleteAll();
        //#endregion

        //#region Roles
        Role r1 = new Role("ADMIN");
        Role r2 = new Role("USER");
        r1 = roleServ.save(r1);
        r2 = roleServ.save(r2);
        //#endregion

        //#region Images
//        Image i1 = new Image("A Placid Lake Beneath the Alps", "https://ipt.imgix.net/205438/x/0/11-tips-for-amazing-autumn-photography-8.jpg", false);
//        i1 = imgServ.save(i1);
//        Image i2 = new Image("Kingfisher Kissing the Alps", "https://static.boredpanda.com/blog/wp-content/uploads/2015/11/perfect-kingfisher-dive-photo-wildlife-photography-alan-mcfayden-311.jpg", false);
//        i2 = imgServ.save(i2);
//        Image i3 = new Image("Buddhist Amitayus Mandala", "https://library.acropolis.org/wp-content/uploads/2015/02/910px-Amitayus_Mandala.jpeg", false);
//        i3 = imgServ.save(i3);
//        Image i4 = new Image("An Enthusiastic Chap Smiles into the Aruban Sun", "https://www.flickr.com/photos/191776651@N05/50844032261/in/dateposted-public/", false);
//        i4 = imgServ.save(i4);
//        Image i5 = new Image("Shopify is the Best", "https://jordiob.com/amazon-tools/wp-content/uploads/2020/08/shopify-apps.png", true);
//        i5 = imgServ.save(i5);
        //#endregion

        //#region Seed user creation

        //admin
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        User admin = new User("admin", "Shopify4Ever", null);
        admin.getRoles()
                .add(new UserRoles(admin, r1));
        admin.getRoles()
                .add(new UserRoles(admin, r2));

        admin = useServ.save(admin);

        User user1 = new User(
                "Lebron James",
                "",
                "l.j@lalakers.com");

        user1.getRoles().add(new UserRoles(user1, r2));

        user1.getImages()
                .add(new Image(
                user1,
            "A Placid Lake Beneath the Alps",
                "https://ipt.imgix.net/205438/x/0/11-tips-for-amazing-autumn-photography-8.jpg",
                false));
        user1.getImages()
                .add(new Image(
                user1,
                "Kingfisher Kissing the Alps",
                "https://static.boredpanda.com/blog/wp-content/uploads/2015/11/perfect-kingfisher-dive-photo-wildlife-photography-alan-mcfayden-311.jpg",
                false));
        user1.getImages()
                .add(new Image(
                user1,
                "Buddhist Amitayus Mandala",
                "https://library.acropolis.org/wp-content/uploads/2015/02/910px-Amitayus_Mandala.jpeg",
                false));

        user1 = useServ.save(user1);

        User user2 = new User(
                "Alex Goncalves",
                "",
                "schroedergoncalves@gmail.com");
        user2.getRoles().add(new UserRoles(user2, r2));
        user2.getImages().add(new Image(
                user2,
                "An Enthusiastic Chap Smiles into the Aruban Sun",
                "https://www.flickr.com/photos/191776651@N05/50844032261/in/dateposted-public/",
                false));
        user2.getImages().add(new Image(
                user2,
                "Shopify is the Best",
                "https://jordiob.com/amazon-tools/wp-content/uploads/2020/08/shopify-apps.png",
                true));
        user2 = useServ.save(user2);
        //#endregion

    }
}
