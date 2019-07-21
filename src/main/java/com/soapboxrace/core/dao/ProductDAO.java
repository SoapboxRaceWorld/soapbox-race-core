package com.soapboxrace.core.dao;

import com.soapboxrace.core.dao.util.BaseDAO;
import com.soapboxrace.core.jpa.ProductEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.List;

@Stateless
public class ProductDAO extends BaseDAO<ProductEntity> {
    private final SecureRandom secureRandom = new SecureRandom();

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProductEntity findById(Long id) {
        return entityManager.find(ProductEntity.class, id);
    }

    public List<ProductEntity> findByLevelEnabled(String categoryName, String productType, int minLevel, boolean enabled, boolean premium) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByLevelEnabled", ProductEntity.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("productType", productType);
        query.setParameter("enabled", enabled);
        query.setParameter("minLevel", minLevel);
        query.setParameter("premium", premium);
        return query.getResultList();
    }

    public List<ProductEntity> findForEndRace(String categoryName, String productType, int level) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findForEndRace", ProductEntity.class);
        query.setParameter("categoryName", categoryName);
        query.setParameter("productType", productType);
        query.setParameter("level", level);
        return query.getResultList();
    }

    public List<ProductEntity> findBySubTypeAndRarity(String subType, Integer rarity) {
        return entityManager.createNamedQuery("ProductEntity.findBySubTypeAndRarity", ProductEntity.class)
                .setParameter("rarity", rarity)
                .setParameter("subType", subType)
                .getResultList();
    }

    public List<ProductEntity> findByProdTypeAndRarity(String prodType, Integer rarity) {
        return entityManager.createNamedQuery("ProductEntity.findByProdTypeAndRarity", ProductEntity.class)
                .setParameter("rarity", rarity)
                .setParameter("prodType", prodType)
                .getResultList();
    }

    public ProductEntity findByProductId(String productId) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByProductId", ProductEntity.class);
        query.setParameter("productId", productId);

        List<ProductEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public ProductEntity findByEntitlementTag(String entitlementTag) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByEntitlementTag", ProductEntity.class);
        query.setParameter("entitlementTag", entitlementTag);

        List<ProductEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public ProductEntity findByHash(Integer hash) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByHash", ProductEntity.class);
        query.setParameter("hash", hash);

        List<ProductEntity> resultList = query.getResultList();
        return !resultList.isEmpty() ? resultList.get(0) : null;
    }

    public List<ProductEntity> findByType(String type) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findByType", ProductEntity.class);
        query.setParameter("type", type);

        return query.getResultList();
    }

    public List<ProductEntity> findDropsByType(String type) {
        TypedQuery<ProductEntity> query = entityManager.createNamedQuery("ProductEntity.findDropsByType", ProductEntity.class);
        query.setParameter("type", type);

        return query.getResultList();
    }

    public ProductEntity getRandomDrop(String productType) {
        List<ProductEntity> productEntities = findDropsByType(productType);

        if (productEntities.isEmpty()) {
            throw new RuntimeException("No droppable products of type '" + productType + "' to work with!");
        }

        double weightSum = Math.ceil(productEntities.stream().mapToDouble(ProductEntity::getDropWeight).sum());

        int randomIndex = -1;
        double random = Math.random() * weightSum;

        for (int i = 0; i < productEntities.size(); i++) {
            random -= productEntities.get(i).getDropWeight();

            if (random <= 0.0d) {
                randomIndex = i;
                break;
            }
        }

        if (randomIndex == -1) {
            throw new RuntimeException("Random selection failed.");
        }

        return productEntities.get(randomIndex);

//        StringBuilder sqlWhere = new StringBuilder();
//        sqlWhere.append(" WHERE obj.isDropable=true ");
//        sqlWhere.append(" AND obj.productType=:productType");
//
//        StringBuilder sqlCount = new StringBuilder();
//        sqlCount.append("SELECT COUNT(*) FROM ProductEntity obj ");
//        sqlCount.append(sqlWhere.toString());
//
//        Query countQuery = entityManager.createQuery(sqlCount.toString());
//        countQuery.setParameter("productType", productType);
//        Long count = (Long) countQuery.getSingleResult();
//
//        if (count == 0) return null;
//
//        Random random = new Random();
//        int number = random.nextInt(count.intValue());
//
//        StringBuilder sqlProduct = new StringBuilder();
//        sqlProduct.append("SELECT obj FROM ProductEntity obj");
//        sqlProduct.append(sqlWhere.toString());
//
//        TypedQuery<ProductEntity> productQuery = entityManager.createQuery(sqlProduct.toString(), ProductEntity.class);
//        productQuery.setParameter("productType", productType);
//
//        productQuery.setFirstResult(number);
//        productQuery.setMaxResults(1);
//        return productQuery.getSingleResult();
    }
}
