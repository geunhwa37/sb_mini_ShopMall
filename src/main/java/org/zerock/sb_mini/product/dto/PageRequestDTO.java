package org.zerock.sb_mini.product.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import lombok.ToString;

@ToString
public class PageRequestDTO {

    private int page = 1;
    private int size = 10;

    // T, C, W, TC, TCW => 각 글자를 문자열 배열 TCW = [T,C,W]
    private String type;
    private String keyword;


    //현재 페이지 + 검색 조건을 포함한 쿼리 스트링 문자열 반환 (?page=1&size=10&type=T&keyword=aaa 형식)
    public String getPageLink() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("?page="+page+"&size="+size);

        buffer.append(getLink());

        return buffer.toString();

    }
    //type과 keyword를 Map<String, Object>로 묶어서 반환
    public Map<String, Object> getLinkMap() {

        Map<String, Object> map = new HashMap<>();

        if(type != null){
            map.put("type",type);
        }
        map.put("keyword",keyword);

        return map;

    }
    //type, keyword만 따로 쿼리스트링 형태로 반환 (&type=T&keyword=aaa)
    public String getLink() {

        if(keyword == null || type == null) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append("&type="+type);

        try {
            buffer.append("&keyword="+URLEncoder.encode(keyword,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();

    }

    //검색 type을 배열로 쪼갬
    //for MyBatis 변수명은 arr
    public String[] getArr() {

        if(type == null || keyword == null) {
            return null;
        }

        return type.split("");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // limit 10 offset 20 (3-1) * size
    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return this.size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {

        if(page < 1) {
            page = 1;
            return;
        }
        if(page > 10000){
            page = 100;
            return;
        }

        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {

        if(size < 10) {
            this.size = 10;
            return;
        }

        if(size > 100){
            this.size = 100;
            return;
        }

        this.size = size;
    }
}