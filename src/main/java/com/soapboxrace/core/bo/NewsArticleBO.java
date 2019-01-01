package com.soapboxrace.core.bo;

import com.soapboxrace.core.dao.NewsArticleDAO;
import com.soapboxrace.core.jpa.NewsArticleEntity;
import com.soapboxrace.jaxb.http.ArrayOfNewsArticleTrans;
import com.soapboxrace.jaxb.http.NewsArticleTrans;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class NewsArticleBO {
    @EJB
    private NewsArticleDAO newsArticleDAO;
    
    public ArrayOfNewsArticleTrans getNewsArticles(Long personaId) {
        ArrayOfNewsArticleTrans arrayOfNewsArticleTrans = new ArrayOfNewsArticleTrans();
        List<NewsArticleEntity> newsArticles = newsArticleDAO.findAllByPersona(personaId);
        
        for (NewsArticleEntity newsArticleEntity : newsArticles) {
            NewsArticleTrans newsArticleTrans = new NewsArticleTrans();
            newsArticleTrans.setNewsId(newsArticleEntity.getId());
            newsArticleTrans.setPersonaId(personaId);
            newsArticleTrans.setTimestamp(newsArticleEntity.getTimestamp());
            newsArticleTrans.setParameters(newsArticleEntity.getParameters());
            newsArticleTrans.setSticky(newsArticleEntity.getSticky());
            newsArticleTrans.setType(newsArticleEntity.getType().getTypeId());
            newsArticleTrans.setShortTextHALId(newsArticleEntity.getShortHALId());
            newsArticleTrans.setLongTextHALId(newsArticleEntity.getLongHALId());
            newsArticleTrans.setFilters(newsArticleEntity.getFilters().getFilterMask());
            newsArticleTrans.setIconType(newsArticleEntity.getIconType());
            
            arrayOfNewsArticleTrans.getNewsArticleTrans().add(newsArticleTrans);
        }
        
        return arrayOfNewsArticleTrans;
    }
}
