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

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}
