package com.gtrobot.model;

import java.io.Serializable;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode();
 * 
 * 登録者, 登録日時, 更新者, 更新日時共通フィールドを加える
 * 
 * <p>
 * <a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @Auther: sunyuxin $LastChangedBy$ $LastChangedRevision$
 *          $LastChangedDate$
 */
public abstract class BaseObject implements Serializable {

	public abstract String toString();

	public abstract boolean equals(Object o);

	public abstract int hashCode();
}
