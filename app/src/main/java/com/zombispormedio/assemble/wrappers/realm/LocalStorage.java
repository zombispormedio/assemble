package com.zombispormedio.assemble.wrappers.realm;


import com.zombispormedio.assemble.wrappers.realm.dao.IBaseDAO;
import com.zombispormedio.assemble.wrappers.realm.dao.IDAOFactory;
import com.zombispormedio.assemble.models.BaseModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Xavier Serrano on 12/09/2016.
 */
public class LocalStorage<D extends RealmObject, M extends BaseModel> {

    private final Class<? extends D> opClass;

    protected final IDAOFactory<D> factory;

    private final Realm database;

    public LocalStorage(Class<D> opClass, IDAOFactory<D> factory) {
        this.opClass = opClass;
        this.factory = factory;
        database = Configuration.getInstance().getDatabase();
    }

    public LocalStorage(Class<D> opClass, IDAOFactory<D> factory, Realm database) {
        this.opClass = opClass;
        this.factory = factory;
        this.database=database;
    }

    public void create(M params) {
        database.beginTransaction();
        D object = factory.create();

        ((IBaseDAO<M>) object).fromModel(params);

        database.copyToRealmOrUpdate(object);

        database.commitTransaction();
    }

    public void save(D object){
        database.copyToRealmOrUpdate(object);
    }

    public void begin() {
        database.beginTransaction();
    }

    public void commit() {
        database.commitTransaction();
    }

    public void update(D object, M params) {
        database.beginTransaction();
        ((IBaseDAO<M>) object).fromModel(params);
        database.copyToRealmOrUpdate(object);
        database.commitTransaction();
    }

    public void updateAll(ArrayList<M> params) {
        database.beginTransaction();
        ArrayList<D> objects = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            D object = factory.create();
            ((IBaseDAO<M>) object).fromModel(params.get(i));
            objects.add(object);
        }
        database.copyToRealmOrUpdate(objects);
        database.commitTransaction();
    }

    public D getById(int id) {
        RealmQuery<D> query = getQuery();

        query.equalTo("id", id);

        return query.findFirst();
    }

    public ArrayList<D> findByAndSort(String keyId, int valueId, String sortKey) {
        RealmQuery<D> query = getQuery();

        query.equalTo(keyId, valueId);

        return toArrayList(query.findAllSorted(sortKey));
    }


    public D findOneByAndSort(String keyId, int valueId, String sortKey) {
        RealmQuery<D> query = getQuery();

        query.equalTo(keyId, valueId);
        RealmResults<D> results=query.findAllSorted(sortKey);
        D result=null;
        if(results.size()>0){
            result=results.last();
        }

        return result;
    }

    public D getFirst() {
        RealmQuery<D> query = getQuery();

        return query.findFirst();
    }

    public ArrayList<D> getAll() {
        RealmQuery<D> query = getQuery();

        RealmResults<D> results = query.findAll();

        return toArrayList(results);
    }

    public void deleteByID(int id) {
        RealmQuery<D> query = getQuery();

        query.equalTo("id", id);

        D object = query.findFirst();

        if (object != null) {
            database.beginTransaction();
            object.deleteFromRealm();
            database.commitTransaction();
        }
    }

    public void delete(D object) {
        if (object != null) {
            database.beginTransaction();
            object.deleteFromRealm();
            database.commitTransaction();
        }
    }

    public ArrayList<D> in(String key, Integer[] values) {
        RealmQuery<D> query = getQuery().in(key, values);

        RealmResults<D> results = query.findAll();

        return toArrayList(results);
    }

    public ArrayList<D> notIn(String key, Integer[] values) {
        RealmQuery<D> query = getQuery().not().in(key, values);

        RealmResults<D> results = query.findAll();

        return toArrayList(results);
    }


    private ArrayList<D> toArrayList(RealmResults<D> realmResults) {
        ArrayList<D> results = new ArrayList<>();

        for (int i = 0; i < realmResults.size(); i++) {
            results.add(realmResults.get(i));
        }
        return results;
    }

    public int countAll() {
        RealmQuery<D> query = getQuery();
        return (int) query.count();
    }


    public void close(){
        database.close();
    }

    public RealmQuery<D> getQuery() {
        return (RealmQuery<D>) database.where(opClass);
    }

    public static class Configuration {

        private static final Configuration ourInstance = new Configuration();

        private Realm database;

        public static Configuration getInstance() {
            return ourInstance;
        }

        public Realm getDatabase() {
            return database;
        }

        public void setDatabase(Realm database) {
            this.database = database;
        }

        public static void deleteAll() {
            Realm realm = ourInstance.database;

            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();

        }
    }

}
