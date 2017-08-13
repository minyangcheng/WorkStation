package com.min.dbcompare.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.min.dbcompare.bean.GreendaoPerson;
import com.min.dbcompare.bean.Student;

import com.min.dbcompare.dao.GreendaoPersonDao;
import com.min.dbcompare.dao.StudentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig greendaoPersonDaoConfig;
    private final DaoConfig studentDaoConfig;

    private final GreendaoPersonDao greendaoPersonDao;
    private final StudentDao studentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        greendaoPersonDaoConfig = daoConfigMap.get(GreendaoPersonDao.class).clone();
        greendaoPersonDaoConfig.initIdentityScope(type);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        greendaoPersonDao = new GreendaoPersonDao(greendaoPersonDaoConfig, this);
        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(GreendaoPerson.class, greendaoPersonDao);
        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        greendaoPersonDaoConfig.clearIdentityScope();
        studentDaoConfig.clearIdentityScope();
    }

    public GreendaoPersonDao getGreendaoPersonDao() {
        return greendaoPersonDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
