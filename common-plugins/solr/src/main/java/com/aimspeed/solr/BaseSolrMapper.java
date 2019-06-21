package com.aimspeed.solr;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import com.aimspeed.solr.vo.PageVo;

/**
 * Solr操作基础类接口
 * @author AimSpeed
 */
public interface BaseSolrMapper<T> {
	
	//=============================== 查找 ===============================
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param id
	 * @return SolrDocument 
	 */
	SolrDocument getById(String id);
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param ids
	 * @return List<SolrDocument>
	 */
	List<SolrDocument> getByIds(Collection<String> ids);
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param id
	 * @param solrParams
	 * @return SolrDocument  
	 */
	SolrDocument getById(String id, SolrParams solrParams);
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param ids
	 * @param solrParams
	 * @return List<SolrDocument>
	 */
	List<SolrDocument> getByIds(Collection<String> ids, SolrParams solrParams);
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param id
	 * @return T  
	 */
	T getByIdToBean(String id);
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param ids
	 * @return List<T>  
	 */
	List<T> getByIdToBeans(Collection<String> ids);
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param id
	 * @param solrParams
	 * @return T  
	 */
	T getByIdToBean(String id, SolrParams solrParams);
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @author AimSpeed
	 * @param ids
	 * @param solrParams
	 * @return T  
	 */
	List<T> getByIdToBeans(Collection<String> ids, SolrParams solrParams);
	
	/**
	 * 根据条件进行查询
	 * @author AimSpeed
	 * @param solrParams
	 * @return QueryResponse  
	 */
	QueryResponse query(SolrQuery solrQuery);

	/**
	 * 将查询结果转为单个对象，如果为多个对象则报错。
	 * @author AimSpeed
	 * @param solrParams
	 * @return T  
	 */
	T queryToBean(SolrQuery solrQuery);
	
	/**
	 * 将查询结果转为单个对象，如果为多个对象则报错。
	 * @author AimSpeed
	 * @param solrParams
	 * @return List<T>  
	 */
	List<T> queryToBeans(SolrQuery solrQuery);

	/**
	 * 分页查询
	 * 多个高亮字段用（,）分割
	 * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * q: 查询字符串（必须的）。:表示查询所有；keyword:东看 表示按关键字“东看”查询 
     * fq: filter query 过滤查询。使用Filter Query可以充分利用Filter Query Cache，提高检索性能。作用：在q查询符合结果中同时是fq查询符合的(类似求交集)，例如：q=mm&fq=date_time:[20081001 TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。 
     * sort: 排序。格式如下：字段名 排序方式；如advertiserId desc 表示按id字段降序排列查询结果。 
     * start,rows:表示查回结果从第几条数据开始显示，共显示多少条。 
     * fl: field list。指定查询结果返回哪些字段。多个时以空格“ ”或逗号“,”分隔。不指定时，默认全返回。 
     * df: default field默认的查询字段，一般默认指定。 
     * Raw Query Parameters: 
     * wt: write type。指定查询输出结果格式，我们常用的有json格式与xml格式。在solrconfig.xml中定义了查询输出格式：xml、json、python、ruby、php、phps、custom。 
     * indent: 返回的结果是否缩进，默认关闭，用 indent=true | on 开启，一般调试json,php,phps,ruby输出才有必要用这个参数。 
     * debugQuery: 设置返回结果是否显示Debug信息。 
     * dismax: 
     * edismax: 
     * hl: high light 高亮。hl=true表示启用高亮 
     * hl.fl ： 用空格或逗号隔开的字段列表（指定高亮的字段）。要启用某个字段的highlight功能，就得保证该字段在schema中是stored。如果该参数未被给出，那么就会高 亮默认字段 standard handler会用df参数，dismax字段用qf参数。你可以使用星号去方便的高亮所有字段。如果你使用了通配符，那么要考虑启用 hl.requiredFieldMatch选项。 
     * hl.simple.pre： 
     * hl.requireFieldMatch: 如果置为true，除非该字段的查询结果不为空才会被高亮。它的默认值是false，意味 着它可能匹配某个字段却高亮一个不同的字段。如果hl.fl使用了通配符，那么就要启用该参数。尽管如此，如果你的查询是all字段（可能是使用 copy-field 指令），那么还是把它设为false，这样搜索结果能表明哪个字段的查询文本未被找到 
     * hl.usePhraseHighlighter：如果一个查询中含有短语（引号框起来的）那么会保证一定要完全匹配短语的才会被高亮。 
     * hl.highlightMultiTerm：如果使用通配符和模糊搜索，那么会确保与通配符匹配的term会高亮。默认为false，同时hl.usePhraseHighlighter要为true。 
     * facet:分组统计，在搜索关键字的同时,能够按照Facet的字段进行分组并统计。 
     * facet.query：Facet Query利用类似于filter query的语法提供了更为灵活的Facet.通过facet.query参数，可以对任意字段进行筛选。 
     * facet.field：需要分组统计的字段，可以多个。 
     * facet.prefix： 表示Facet字段值的前缀。比如facet.field=cpu&facet.prefix=Intel，那么对cpu字段进行Facet查询，返回的cpu都是以Intel开头的， AMD开头的cpu型号将不会被统计在内。 
     * spatial: 
     * spellcheck: 拼写检查。
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param solrParams
	 * @return PageVo<T>  
	 */
	PageVo<T> queryPage(Integer curPage, Integer pageSize,String orderField, String orderingRule,String query);
	
	/**
	 * 分页查询
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return SolrPageVo<T>  
	 */
	PageVo<T> queryPage(Integer curPage, Integer pageSize,String orderField, String orderingRule, Map<String, Object> filtrate, Map<String, Object> likeFiltrate);
	
	/**
	 * 分页查询，并使用默认的高亮显示，需要重写{@link #queryPageAssembleResultOfHighlight()}
	 * 多个高亮字段用（,）分割
	 * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * q: 查询字符串（必须的）。:表示查询所有；keyword:东看 表示按关键字“东看”查询 
     * fq: filter query 过滤查询。使用Filter Query可以充分利用Filter Query Cache，提高检索性能。作用：在q查询符合结果中同时是fq查询符合的(类似求交集)，例如：q=mm&fq=date_time:[20081001 TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。 
     * sort: 排序。格式如下：字段名 排序方式；如advertiserId desc 表示按id字段降序排列查询结果。 
     * start,rows:表示查回结果从第几条数据开始显示，共显示多少条。 
     * fl: field list。指定查询结果返回哪些字段。多个时以空格“ ”或逗号“,”分隔。不指定时，默认全返回。 
     * df: default field默认的查询字段，一般默认指定。 
     * Raw Query Parameters: 
     * wt: write type。指定查询输出结果格式，我们常用的有json格式与xml格式。在solrconfig.xml中定义了查询输出格式：xml、json、python、ruby、php、phps、custom。 
     * indent: 返回的结果是否缩进，默认关闭，用 indent=true | on 开启，一般调试json,php,phps,ruby输出才有必要用这个参数。 
     * debugQuery: 设置返回结果是否显示Debug信息。 
     * dismax: 
     * edismax: 
     * hl: high light 高亮。hl=true表示启用高亮 
     * hl.fl ： 用空格或逗号隔开的字段列表（指定高亮的字段）。要启用某个字段的highlight功能，就得保证该字段在schema中是stored。如果该参数未被给出，那么就会高 亮默认字段 standard handler会用df参数，dismax字段用qf参数。你可以使用星号去方便的高亮所有字段。如果你使用了通配符，那么要考虑启用 hl.requiredFieldMatch选项。 
     * hl.simple.pre： 
     * hl.requireFieldMatch: 如果置为true，除非该字段的查询结果不为空才会被高亮。它的默认值是false，意味 着它可能匹配某个字段却高亮一个不同的字段。如果hl.fl使用了通配符，那么就要启用该参数。尽管如此，如果你的查询是all字段（可能是使用 copy-field 指令），那么还是把它设为false，这样搜索结果能表明哪个字段的查询文本未被找到 
     * hl.usePhraseHighlighter：如果一个查询中含有短语（引号框起来的）那么会保证一定要完全匹配短语的才会被高亮。 
     * hl.highlightMultiTerm：如果使用通配符和模糊搜索，那么会确保与通配符匹配的term会高亮。默认为false，同时hl.usePhraseHighlighter要为true。 
     * facet:分组统计，在搜索关键字的同时,能够按照Facet的字段进行分组并统计。 
     * facet.query：Facet Query利用类似于filter query的语法提供了更为灵活的Facet.通过facet.query参数，可以对任意字段进行筛选。 
     * facet.field：需要分组统计的字段，可以多个。 
     * facet.prefix： 表示Facet字段值的前缀。比如facet.field=cpu&facet.prefix=Intel，那么对cpu字段进行Facet查询，返回的cpu都是以Intel开头的， AMD开头的cpu型号将不会被统计在内。 
     * spatial: 
     * spellcheck: 拼写检查。
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param solrParams
	 * @return PageVo<T>  
	 */
	PageVo<T> queryPageToDefaulHighlight(Integer curPage, Integer pageSize,String orderField,String orderingRule,String highlightField,String query);
	
	/**
	 * 分页查询，需要重写{@link #queryPageToHighlight()},{@link #queryPageAssembleResultOfHighlight()}
	 * 多个高亮字段用（,）分割
	 * 综合查询: 在综合查询中, 有按条件查询, 条件过滤, 排序, 分页, 高亮显示, 获取部分域信息
     * q: 查询字符串（必须的）。:表示查询所有；keyword:东看 表示按关键字“东看”查询 
     * fq: filter query 过滤查询。使用Filter Query可以充分利用Filter Query Cache，提高检索性能。作用：在q查询符合结果中同时是fq查询符合的(类似求交集)，例如：q=mm&fq=date_time:[20081001 TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。 
     * sort: 排序。格式如下：字段名 排序方式；如advertiserId desc 表示按id字段降序排列查询结果。 
     * start,rows:表示查回结果从第几条数据开始显示，共显示多少条。 
     * fl: field list。指定查询结果返回哪些字段。多个时以空格“ ”或逗号“,”分隔。不指定时，默认全返回。 
     * df: default field默认的查询字段，一般默认指定。 
     * Raw Query Parameters: 
     * wt: write type。指定查询输出结果格式，我们常用的有json格式与xml格式。在solrconfig.xml中定义了查询输出格式：xml、json、python、ruby、php、phps、custom。 
     * indent: 返回的结果是否缩进，默认关闭，用 indent=true | on 开启，一般调试json,php,phps,ruby输出才有必要用这个参数。 
     * debugQuery: 设置返回结果是否显示Debug信息。 
     * dismax: 
     * edismax: 
     * hl: high light 高亮。hl=true表示启用高亮 
     * hl.fl ： 用空格或逗号隔开的字段列表（指定高亮的字段）。要启用某个字段的highlight功能，就得保证该字段在schema中是stored。如果该参数未被给出，那么就会高 亮默认字段 standard handler会用df参数，dismax字段用qf参数。你可以使用星号去方便的高亮所有字段。如果你使用了通配符，那么要考虑启用 hl.requiredFieldMatch选项。 
     * hl.simple.pre： 
     * hl.requireFieldMatch: 如果置为true，除非该字段的查询结果不为空才会被高亮。它的默认值是false，意味 着它可能匹配某个字段却高亮一个不同的字段。如果hl.fl使用了通配符，那么就要启用该参数。尽管如此，如果你的查询是all字段（可能是使用 copy-field 指令），那么还是把它设为false，这样搜索结果能表明哪个字段的查询文本未被找到 
     * hl.usePhraseHighlighter：如果一个查询中含有短语（引号框起来的）那么会保证一定要完全匹配短语的才会被高亮。 
     * hl.highlightMultiTerm：如果使用通配符和模糊搜索，那么会确保与通配符匹配的term会高亮。默认为false，同时hl.usePhraseHighlighter要为true。 
     * facet:分组统计，在搜索关键字的同时,能够按照Facet的字段进行分组并统计。 
     * facet.query：Facet Query利用类似于filter query的语法提供了更为灵活的Facet.通过facet.query参数，可以对任意字段进行筛选。 
     * facet.field：需要分组统计的字段，可以多个。 
     * facet.prefix： 表示Facet字段值的前缀。比如facet.field=cpu&facet.prefix=Intel，那么对cpu字段进行Facet查询，返回的cpu都是以Intel开头的， AMD开头的cpu型号将不会被统计在内。 
     * spatial: 
     * spellcheck: 拼写检查。
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return SolrPageVo<T>  
	 */
	PageVo<T> queryPageToHighlight(Integer curPage, Integer pageSize,String orderField,String orderingRule,Map<String, Object> filtrate, Map<String, Object> likeFiltrate);
	
	/**
	 * 根据条件进行查询，并使用默认的高亮显示，需要调用者自己设置高亮字段
	 * @author AimSpeed
	 * @param solrParams
	 * @return QueryResponse  
	 */
	QueryResponse queryToDefaulHighlight(SolrQuery solrQuery);
	
	/**
	 * 查找数据量
	 * @author AimSpeed
	 * @param solrQuery
	 * @return Integer 
	 */
	Integer countSize(SolrQuery solrQuery);
	
	//=============================== 添加 ===============================
	
	/**
	 * 单个插入
	 * @author AimSpeed
	 * @param obj
	 * @return boolean  
	 */
	boolean insert(T record);

	/**
	 * 批量插入
	 * @author AimSpeed
	 * @param records
	 * @return boolean  
	 */
	boolean batchInsertBean(Collection<T> records);
	
	/**
	 * 通过自由定义属性单个添加
	 * 例如：SolrInputDocument doc = new SolrInputDocument();
     *		doc.setField("id", "499dc174aec04263801a2181a730265b");
     *		doc.setField("name", "我是中国人AAA, 我爱中国");
	 * @author AimSpeed
	 * @param solrInputDocument
	 * @return boolean  
	 */
	boolean insert(SolrInputDocument solrInputDocument);

	/**
	 * 批量插入
	 * @author AimSpeed
	 * @param solrInputDocuments
	 * @return boolean  
	 */
	boolean batchInsertDocument(Collection<SolrInputDocument> solrInputDocuments);
	
	//=============================== 更新 ===============================
	
	/**
	 * 根据Id单个更新，Id不存在则不更新
	 * @author AimSpeed
	 * @param record
	 * @return boolean  
	 */
	boolean updateById(T record);
	
	/**
	 * 批量更新，如果Id不存在则插入（无法保证Id不存在就不更新）
	 * @author AimSpeed
	 * @param records
	 * @return boolean  
	 */
	boolean batchUpdateBeanById(Collection<T> records);
	
	/**
	 * 通过自由定义属性单个更新，Id不存在则不更新
	 * 例如：SolrInputDocument doc = new SolrInputDocument();
     *		doc.setField("id", "499dc174aec04263801a2181a730265b");
     *		doc.setField("name", "我是中国人AAA, 我爱中国");
	 * @author AimSpeed
	 * @param solrInputDocument
	 * @return boolean  
	 */
	boolean updateById(SolrInputDocument solrInputDocument);
	
	/**
	 * 批量更新，如果Id不存在则插入（无法保证Id不存在就不更新）
	 * @author AimSpeed
	 * @param solrInputDocuments
	 * @return boolean  
	 */
	boolean batchUpdateDocumentById(Collection<SolrInputDocument> solrInputDocuments);
	
	//=============================== 删除 ===============================

	/**
	 * 根据多个Id进行删除
	 * @author AimSpeed
	 * @param ids
	 * @return String []
	 */
	boolean deleteByIds(String [] ids);
	
	/**
	 * 根据多个Id进行删除
	 * @author AimSpeed
	 * @param ids
	 * @return List<String> 
	 */
	boolean deleteByIds(List<String> ids);
	
	/**
	 * 根据Solr查询语句进行删除
	 * @author AimSpeed
	 * @param query
	 * @return boolean  
	 */
	boolean deleteByQuery(String query);
	
	
	
	
	
}
