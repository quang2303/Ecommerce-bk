package com.ecommerce.be.Repository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.ecommerce.be.Constant.SortOrderConstant;
import com.ecommerce.be.Dto.ProductFilterForAdminPageDTO;
import com.ecommerce.be.Dto.ProductFilterForSellerPageDTO;
import com.ecommerce.be.Entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    

    @Override
    public List<Product> findByFilterInAdminPage(ProductFilterForAdminPageDTO filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filters.product() != null) {
            predicates.add(cb.like(product.get("name"), "%" + filters.product() + "%"));
        }
        if (filters.seller() != null) {
            predicates.add(cb.equal(product.get("seller"), filters.seller()));
        }
        if (filters.collab() != null) {
            predicates.add(cb.equal(product.get("approver"), filters.collab()));
        }
        if (filters.startPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), filters.startPrice()));
        }
        if (filters.endPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), filters.endPrice()));
        }
        if (filters.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("approvedAt").as(LocalDate.class), filters.startDate()));
        }
        if (filters.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("approvedAt").as(LocalDate.class), filters.endDate()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Handle ordering
        if (filters.orders() != null) {
            switch (filters.orders()) {
                case SortOrderConstant.NEW_TO_OLD:
                    query.orderBy(cb.desc(product.get("approvedAt")));
                    break;
                case SortOrderConstant.OLD_TO_NEW:
                    query.orderBy(cb.asc(product.get("approvedAt")));
                    break;
                case SortOrderConstant.LOW_TO_HIGH:
                    query.orderBy(cb.asc(product.get("price")));
                    break;
                default:
                    query.orderBy(cb.desc(product.get("price")));
                    break;
            }
        }

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        // Handle pagination
        if (filters.offset() != null && filters.page() != null) {
            int offset = filters.offset();
            int page = filters.page();
            typedQuery.setFirstResult(offset * (page - 1));
            typedQuery.setMaxResults(offset);
        } else {
            typedQuery.setFirstResult(0);
            typedQuery.setMaxResults(10);
        }

        return typedQuery.getResultList();
    }


    @Override
    public List<Product> findByFilterInSellerPage(ProductFilterForSellerPageDTO filters, String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.like(product.get("name"), "%" + filters.searchTerm() + "%"));
        predicates.add(cb.equal(product.get("seller").get("username"), username));

        if (filters.status() != null) {
            predicates.add(cb.equal(product.get("status"), filters.status()));
        }

        if (filters.isSoldOut() != null && filters.isSoldOut() == true) {
            predicates.add(cb.equal(product.get("quantity"), 0));
        }
        else if (filters.isSoldOut() != null && filters.isSoldOut() == false) {
            predicates.add(cb.greaterThan(product.get("quantity"), 0));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        // Handle pagination
        if (filters.offset() != null && filters.page() != null) {
            int offset = filters.offset();
            int page = filters.page();
            typedQuery.setFirstResult(offset * (page - 1));
            typedQuery.setMaxResults(offset);
        } else {
            typedQuery.setFirstResult(0);
            typedQuery.setMaxResults(10);
        }
        return typedQuery.getResultList();
    }
}
