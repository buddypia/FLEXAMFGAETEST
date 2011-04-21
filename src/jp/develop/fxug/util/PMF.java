package jp.develop.fxug.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory instance =
		JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF() {}
	public static PersistenceManager getPersistenceManager() {
		return instance.getPersistenceManager();
	}
}
