package com.min.seed.db.delegate;

import com.blankj.utilcode.util.EmptyUtils;
import com.min.seed.db.DBOperate;
import com.min.seed.db.bean.SearchBean;
import com.min.seed.db.dao.DaoSession;
import com.min.seed.db.dao.SearchBeanDao;

import java.util.Date;
import java.util.List;

public class SearchDaoDelegate {

    private SearchBeanDao dao;

    public SearchDaoDelegate() {
        DaoSession daoSession = DBOperate.getInstance().getDaoSession();
        dao = daoSession.getSearchBeanDao();
    }

    public void save(String searchStr) {
        if (EmptyUtils.isEmpty(searchStr)) {
            return;
        }
        SearchBean bean = new SearchBean();
        bean.setSearchStr(searchStr);
        bean.setTime(new Date().getTime());
        bean.setUserid(1);

        List<SearchBean> searchBeanList = dao.queryBuilder()
                .where(SearchBeanDao.Properties.Userid.eq(bean.getUserid()), SearchBeanDao.Properties.SearchStr.eq(bean.getSearchStr()))
                .list();
        if (!EmptyUtils.isEmpty(searchBeanList)) {
            dao.deleteInTx(searchBeanList);
        }
        dao.save(bean);
    }

    public List<SearchBean> query() {
        return dao.queryBuilder()
                .where(SearchBeanDao.Properties.Userid.eq(1))
                .orderDesc(SearchBeanDao.Properties.Time)
                .list();
    }

    public void clear() {
        dao.deleteAll();
    }

}
