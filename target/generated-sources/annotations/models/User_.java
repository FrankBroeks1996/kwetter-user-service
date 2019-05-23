package models;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> website;
	public static volatile SingularAttribute<User, Role> role;
	public static volatile SetAttribute<User, User> followers;
	public static volatile SetAttribute<User, User> following;
	public static volatile SingularAttribute<User, String> bio;
	public static volatile ListAttribute<User, UUID> kweets;
	public static volatile SingularAttribute<User, String> location;
	public static volatile SingularAttribute<User, UUID> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> profilePicturePath;

}

