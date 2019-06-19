package daos;

import database.memory.InMemoryDatabase;
import dtos.UserDTO;
import models.Role;
import models.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Specializes;

//@RunWith(Arquillian.class)
//public class UserDAOMemoryImplTest extends UserDAOTest{
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(IUserDAO.class)
//                .addClass(UserDAOMemoryImpl.class)
//                .addClass(UserDAOTest.class)
//                .addClass(User.class)
//                .addClass(Role.class)
//                .addClass(UserDTO.class)
//                .addClass(InMemoryDatabase.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @After
//    public void cleanUp(){
//        InMemoryDatabase.getInMemoryDatabase().cleanUp();
//    }
//}
