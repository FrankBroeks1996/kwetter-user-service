package daos;

import dtos.UserDTO;
import models.Role;
import models.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@RunWith(Arquillian.class)
//public class UserDAOImplTest extends UserDAOTest {
//    public UserDAOImplTest() {
//    }
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(IUserDAO.class)
//                .addClass(UserDAOImpl.class)
//                .addClass(UserDAOTest.class)
//                .addClass(User.class)
//                .addClass(Role.class)
//                .addClass(UserDTO.class)
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//}
