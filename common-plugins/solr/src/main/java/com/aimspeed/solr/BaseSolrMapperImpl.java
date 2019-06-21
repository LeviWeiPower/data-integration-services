package com.aimspeed.solr;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import com.aimspeed.common.constants.RepositorySearchConstants;
import com.aimspeed.common.datatype.StringUtils;
import com.aimspeed.solr.utils.SolrUtils;
import com.aimspeed.solr.vo.PageVo;

/**
 * Solr操作基础接口实现类
 * 
 * Solr基本概念：
 * 1 、 Core： 在Solr的单节点部署或者Master-Salve方式部署下标示一个完整索引。Core都是由多个文件组成，建立索引的时候是先分段，然后再合并的方式。
 * 		一个Solr可以包含一个或多个Core，每个Core的配置可以不同；在SolrCoud部署下标示索引的一部分。
 * 1、Document是Solr索引（动词，indexing）和搜索的最基本单元，它类似于关系数据库表中的一条记录，可以包含一个或多个字段（Field），每个字段包含一个name和文本值。
 * 2、Field在Solr中，字段(Field)是构成Document的基本单元。对应于数据库表中的某一列。字段是包括了名称，类型以及对字段对应的值如何处理的一种元数据。
 * 		1）name：名称（相当于数据库表的字段名）
 * 		2）type：类型（相当于数据库表的字段类型，此类型是在<types></types>节点里面定义好的）
 * 		3）indexed：是否索引（字段是否要建立索引，关系到搜索和排序，基本上都要吧，哈哈）
 * 		4）stored：是否存储（字段的值是否完整的存储在solr里）
 * 		5）multiValued：是否有多个值
 * 3、Field Type：告诉solr如何去处理某个field的数据，以及这个Field在查询的时候处理。
 * 
 * 4、Collection：在SolrCould部署模式下，指的是一个索引的逻辑概念。可以把Solr中的Core或Collection看做一个Oracle的实例。一个SolrCould可以包含多个Collection。
 * 一个Collection可以切分成多个Shard，切分的数量大小和机器节点数和副本数有关系，要求shard数量*副本数<节点数*numShards 。注意一个节点标示启动一个HTTP服务器，
 * 一台机器可以起多个Solr节点（这种情况必须设置不同的端口），一个Collection的内容是由每个shard中的信息组成。一个shard的信息对应组成它的一个副本的信息。
 * 5、Shard： 标示切片，在SolrColud 部署模式下，将一个逻辑索引Collection切割成多个分片， 每个Shard是由多个副本Replica组成。
 * 6、Replica： 副本。多个副本组成一个Shard、注意一个Shard中的replica 包含的内容逻辑上应该是一样的，Shard的数据只是其中一份Replica，不是这些副本的组合。
 * 这些副本中有一个副本会被选择为Leader，负责写索引。
 * 7、Zookeeper：另外一个开源的软件，在solrCloud部署模式下是必须的，主要作用是：
 * 		1）配置的统一存储和分发；
 * 		2）shard中副本的Leader的选取；
 * 		3）负责监控集群状态，发生改变时候通知相关的监听器，比如挂了一台机器，这台机器上如果有shard的leader节点，剩余的同一个shard的其他副本会竞选Leader，且solrColud会知道这台机器挂掉，在处理请求的时候就不发请求给这台机器。
 * 8、Config Set：配置组，存储配置信息，每个Collection都有，至少包含solrconfig.xml这个是配置这个collection基本配置，比如使用的Lucene的版本、使用的查询组件、缓存相关信息等；还必须包含Schema.Xml 这个配置文件配置的是Collection存在的文档的字段，包括字段的类型，是否需要存储，是否需要分词等。
 * 9、文档：在Solr中建立索引是通过文档添加的方式进行，如果将索引看成一张表，那么文档可以看成是一条记录，那么Schema.xml可以看成这个表的定义。
 * 
 * --- Solr ---   --- MySQL ---
 * 	   Core            表
 *     Field		       字段
 *     Field Type      字段类型
 * 
 * Solr语法： 
 * 语法：Field:关键词
 * 例如：name:java  在title字段查询关键词为java的
 *     AND查询：name:java AND title:PHP  同时满足
 *     OOR查询：name:java OR title:PHP 满足其一即可
 * 
 * 注意：空格、AND和OR必须大写
 * 
 * -------------------- 
 * 
 * 实现类必须要继承BaseSolrBean，不然没办法进行转换，接口就不一定了。
 * 如果有需要则可以直接重写接口
 * 
 * 除非有必要如分页等，开发者则可以写一个实现类，继承this。
 * 
 * 查询的说明：
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
 * 
 * @author AimSpeed
 */
public class BaseSolrMapperImpl<T> implements BaseSolrMapper<T> {

	/**
	 * 日志记录
	 */
	private Logger logger = LoggerFactory.getLogger(BaseSolrMapperImpl.class);
	
	private String coreName;
	
	public BaseSolrMapperImpl(String coreName) {
		this.coreName = coreName;
	}
	
	@Autowired
    private SolrClient solrClient;
	
	//=============================== 查找 ===============================

	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getById(java.lang.String)
	 */
	@Override
	public SolrDocument getById(String id){
		try {
			return solrClient.getById(coreName,id);
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getById(java.util.Collection)
	 */
	@Override
	public List<SolrDocument> getByIds(Collection<String> ids){
		try {
			return solrClient.getById(coreName,ids);
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @param id
	 * @param solrParams
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getById(java.lang.String, org.apache.solr.common.params.SolrParams)
	 */
	@Override
	public SolrDocument getById(String id, SolrParams solrParams){
		try {
			return solrClient.getById(coreName, id,solrParams);
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @param ids
	 * @param solrParams
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getById(java.util.Collection, org.apache.solr.common.params.SolrParams)
	 */
	@Override
	public List<SolrDocument> getByIds(Collection<String> ids, SolrParams solrParams){
		try {
			return solrClient.getById(coreName, ids,solrParams);
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getByIdToBean(java.lang.String)
	 */
	@Override
	public T getByIdToBean(String id){
		SolrDocument solrDocument = getById(id);
		if(null != solrDocument) {
			return SolrUtils.solrDocumentConvertToBean(this.getClass(),solrDocument);
		}
		return null;
	}
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getByIdToBean(java.util.Collection)
	 */
	@Override
	public List<T> getByIdToBeans(Collection<String> ids){
		List<SolrDocument> solrDocuments = getByIds(ids);
		if(null != solrDocuments && solrDocuments.size() > 0) {
			return SolrUtils.solrDocumentListConvertToBean(this.getClass(),solrDocuments);
		}
		return null;
	}
	
	/**
	 * 根据ID进行获取并转换为对应的对象数据
	 * @param id
	 * @param solrParams
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getByIdToBean(java.lang.String, org.apache.solr.common.params.SolrParams)
	 */
	@Override
	public T getByIdToBean(String id, SolrParams solrParams){
		SolrDocument solrDocument = getById(id,solrParams);
		if(null != solrDocument) {
			return SolrUtils.solrDocumentConvertToBean(this.getClass(),solrDocument);
		}
		return null;
	}
	
	/**
	 * 根据多个ID进行获取并转换为对应的对象数据
	 * @param ids
	 * @param solrParams
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#getByIdToBean(java.util.Collection, org.apache.solr.common.params.SolrParams)
	 */
	@Override
	public List<T> getByIdToBeans(Collection<String> ids, SolrParams solrParams){
		List<SolrDocument> solrDocuments = getByIds(ids,solrParams);
		if(null != solrDocuments && solrDocuments.size() > 0) {
			return SolrUtils.solrDocumentListConvertToBean(this.getClass(),solrDocuments);
		}
		return null;
	}
	
	/**
	 * 根据条件进行查询
	 * @author AimSpeed
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#query(org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public QueryResponse query(SolrQuery solrQuery) {
		try {
			//为空，则默认查询所有
			if(null == solrQuery) {
				solrQuery = new SolrQuery();
				solrQuery.set("q", "*:*");
			}
			QueryResponse queryResponse = solrClient.query(coreName, solrQuery);
			return queryResponse;
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 将查询结果转为单个对象，如果为多个对象则报错。
	 * @author AimSpeed
	 * @param solrQuery
	 * @return
	 * @throws RuntimeException
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#queryToBean(org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public T queryToBean(SolrQuery solrQuery) {
		//为空，则默认查询所有
		if(null == solrQuery) {
			solrQuery = new SolrQuery();
			solrQuery.set("q", "*:*");
		}
		QueryResponse queryResponse = query(solrQuery);
		if(null == queryResponse) {
			return null;
		}
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//查找出多个则抛出异常
		if(null != solrDocumentList && solrDocumentList.size() > 1) {
			logger.error("查找出多个值!");
			throw new RuntimeException("查找出多个值!");
		}
		
		//单个则直接转换
		if(null != solrDocumentList && solrDocumentList.size() == 1) {
			SolrDocument solrDocument = solrDocumentList.get(0);
			if(null != solrDocument) {
				return SolrUtils.solrDocumentConvertToBean(this.getClass(),solrDocument);
			}
		}
			
		return null;
	}
	
	/**
	 * 将查询结果转为单个对象，如果为多个对象则报错。
	 * @author AimSpeed
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#queryToBeans(org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public List<T> queryToBeans(SolrQuery solrQuery) {
		try {
			//为空，则默认查询所有
			if(null == solrQuery) {
				solrQuery = new SolrQuery();
				solrQuery.set("q", "*:*");
			}
			QueryResponse queryResponse = solrClient.query(coreName,solrQuery);
			if(null == queryResponse) {
				return null;
			}
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			//转换对象
			if(null != solrDocumentList && solrDocumentList.size() > 0) {
				return SolrUtils.solrDocumentListConvertToBean(this.getClass(),solrDocumentList);
			}
			
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return null;
	}

	/**
	 * 分页查询
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#queryPage(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public PageVo<T> queryPage(Integer curPage, Integer pageSize,String orderField, String orderingRule,String query) {
		//为空，则默认查询所有
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", null == query || "".equals(query.trim()) ? "*:*" : query);
		
		//排序，默认是ASC
        if(null != orderingRule && !"".equals(orderingRule.trim())) {
        	solrQuery.addSort(orderField, SolrQuery.ORDER.asc);
        	
        	if(null != orderingRule && RepositorySearchConstants.SORT_DESC.equalsIgnoreCase(orderingRule)) {
    			solrQuery.addSort(orderField, SolrQuery.ORDER.desc);
    		}
        }
		
		// 设置分页 start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
        solrQuery.setStart((Math.max(curPage, 1) - 1) * pageSize);
        solrQuery.setRows(pageSize);
		
        List<T> resultList = queryToBeans(solrQuery);
        Integer countSize = countSize(solrQuery);
        
        //查询出来的数据
  		PageVo<T> page = new PageVo<>();
  		page.setFristPage(1);
  		page.setTotalCount(countSize);//总条数
//  		page.setFiltrate();//保留筛选条件
  		page.setPageSize(pageSize);
  		page.setCurPage(curPage);
  		page.setTotalPage(page.getTotalPage() );
  		page.setNextPage(page.getNextPage());
  		page.setOrderField(orderField);
  		page.setOrderingRule(orderingRule);
  		page.setPageData(resultList);
  		page.setPrePage(page.getPrePage());
		return page;
	}
	
	/**
	 * 分页查询
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return
	 * @overridden @see com.aimspeed.solr.repository.BaseSolrMapper#queryPage(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public PageVo<T> queryPage(Integer curPage, Integer pageSize,String orderField, String orderingRule, Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		//拼接查询字符串
		String query = SolrUtils.jointQueryStr(filtrate, likeFiltrate);
		/*String query = "";
		if(null != likeFiltrate && likeFiltrate.size() > 0) {
			query = SolrUtils.mapConvertToQStringOfLike(likeFiltrate, SolrConstant.CONNECTOR_OR);
		}
		if(null != filtrate && filtrate.size() > 0) {
			query = SolrUtils.mapConvertToQString(filtrate, SolrConstant.CONNECTOR_AND);
		}
		//默认查询
		if("".equals(query.trim())) {
			query = "*:*";
		}*/
		//查询数据
		PageVo<T> solrPageVo = queryPage(curPage, pageSize, orderField, orderingRule, query);
		solrPageVo.setLikeFiltrate(likeFiltrate);
		solrPageVo.setFiltrate(filtrate);
		return solrPageVo;
	}
	
	/**
	 * 分页查询，并使用默认的高亮显示，需要重写{@link #queryPageAssembleResult()}
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#queryPageToDefaulHighlight(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public PageVo<T> queryPageToDefaulHighlight(Integer curPage, Integer pageSize,String orderField, String orderingRule,String highlightField,String query) {
		//为空，则默认查询所有
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", query);
//		solrQuery.addHighlightField(highlightField);
		
		// 设置分页 start=0就是从0开始，，rows=5当前返回5条记录，第二页就是变化start这个值为5就可以了。
        solrQuery.setStart((Math.max(curPage, 1) - 1) * pageSize);
        solrQuery.setRows(pageSize);
        
		//排序，默认是ASC
        if(null != orderingRule && !"".equals(orderingRule.trim())) {
        	solrQuery.addSort(orderField, SolrQuery.ORDER.asc);
        	
        	if(null != orderingRule && RepositorySearchConstants.SORT_DESC.equalsIgnoreCase(orderingRule)) {
    			solrQuery.addSort(orderField, SolrQuery.ORDER.desc);
    		}
        }
		
		//如果不传入高亮字段，默认则所有字段
		if(null == highlightField || "".equals(highlightField.trim())) {
			highlightField = "*";
		}
		solrQuery.addHighlightField(highlightField);
		
		//查询结果并组装，组装则由各自指定类进行高亮结果组装
        QueryResponse queryResponse = queryToDefaulHighlight(solrQuery);
		return queryPageAssembleResult(curPage, pageSize, orderField, orderingRule,true, queryResponse);
	}
	
	/**
	 * 因为查询的是高亮，根据结果进行组装
	 * @author AimSpeed
	 * @param pageVo
	 * @return PageVo<T> 
	 */
	protected PageVo<T> queryPageAssembleResult(Integer curPage, Integer pageSize,String orderField, String orderingRule,boolean highlight,QueryResponse queryResponse){
		return null;
	}

	/**
	 * 分页查询，高亮显示，需要重写{@link #queryPageToHighlight(),@link #queryPageAssembleResultOfHighlight()}
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param filtrate
	 * @param likeFiltrate
	 * @return
	 * @overridden @see com.aimspeed.solr.repository.BaseSolrMapper#queryPageToDefaulHighlight(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.util.Map, java.util.Map)
	 */
	@Override
	public PageVo<T> queryPageToHighlight(Integer curPage, Integer pageSize, String orderField,
			String orderingRule, Map<String, Object> filtrate, Map<String, Object> likeFiltrate) {
		
		//拼接查询字符串
		String query = SolrUtils.jointQueryStr(filtrate, likeFiltrate);
		/*String query = "";
		if(null != filtrate && filtrate.size() > 0) {
			query += SolrUtils.mapConvertToQString(filtrate, SolrConstant.CONNECTOR_AND);
		}
		
		if(null != likeFiltrate && likeFiltrate.size() > 0) {
			String likeQuery = SolrUtils.mapConvertToQStringOfLike(likeFiltrate, SolrConstant.CONNECTOR_OR);
			
			//组装
			if(null != likeQuery && !"".equals(likeQuery.trim())) {
				if(!"".equals(query.trim())) {
					query += " " + SolrConstant.CONNECTOR_AND + " (";
				}
				query += likeQuery;
				query += ")";
			}
		}
		
		//默认查询
		if("".equals(query.trim())) {
			query = "*:*";
		}*/
		
		orderField = StringUtils.lineToHump(orderField);

		//是否启用高亮
		boolean highlight = null != likeFiltrate && likeFiltrate.size() > 0 ? true : false;
		
		//查询数据
		PageVo<T> solrPageVo = queryPageToHighlight(curPage, pageSize, orderField,
				orderingRule, query,highlight);
		solrPageVo.setLikeFiltrate(likeFiltrate);
		solrPageVo.setFiltrate(filtrate);
		return solrPageVo;
	}
	
	/**
	 * 根据自我需求重写，分页，如果是需要根据高亮的话，那么则需要重写
	 * @author AimSpeed
	 * @param curPage
	 * @param pageSize
	 * @param orderField
	 * @param orderingRule
	 * @param query
	 * @return SolrPageVo<T>  
	 */
	protected PageVo<T> queryPageToHighlight(Integer curPage, Integer pageSize, String orderField,
			String orderingRule, String query,boolean highlight){
		return null;
	}
	
	/**
	 * 根据条件进行查询，并使用默认的高亮显示，需要调用者自己设置高亮字段
	 * @author AimSpeed
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#queryToDefaulHighlight(org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public QueryResponse queryToDefaulHighlight(SolrQuery solrQuery) {
		
		//为空，则默认查询所有
		if(null == solrQuery) {
			solrQuery = new SolrQuery();
			solrQuery.set("q", "*:*");
		}
        //高亮
        //打开开关
		solrQuery.setHighlight(true);
        //指定高亮域
//		solrQuery.addHighlightField("*");
        //设置前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
        //设置后缀
		solrQuery.setHighlightSimplePost("</span>");
		try {
			QueryResponse queryResponse = solrClient.query(coreName, solrQuery);
			return queryResponse;
		} catch (SolrServerException | IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 查找数据量
	 * @author AimSpeed
	 * @param solrQuery
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#countSize(org.apache.solr.client.solrj.SolrQuery)
	 */
	@Override
	public Integer countSize(SolrQuery solrQuery) {
		QueryResponse queryResponse = query(solrQuery);
		if(null != queryResponse && null != queryResponse.getResults()) {
			return queryResponse.getResults().size();
		}
		return 0;
	}
	
	//=============================== 添加 ===============================

	/**
	 * 单个插入
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(T record) {
		try {
			
			/*solrClient.getById(collection, id)
			solrClient.getById(collection, ids)
			solrClient.getById(collection, ids, params)
			solrClient.getById(collection, id, params)*/
			
			//根据Id删除
			solrClient.addBean(coreName, record);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

	/**
	 * 批量插入
	 * @param records
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#batchInsertBean(java.util.Collection)
	 */
	@Override
	public boolean batchInsertBean(Collection<T> records) {
		try {
			//根据Id删除
			solrClient.addBeans(coreName, records);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}
	
	/**
	 * 通过自由定义属性单个添加
	 * 例如：SolrInputDocument doc = new SolrInputDocument();
     *		doc.setField("id", "499dc174aec04263801a2181a730265b");
     *		doc.setField("name", "我是中国人AAA, 我爱中国");
	 * @param solrInputDocument
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#insert(org.apache.solr.common.SolrInputDocument)
	 */
	@Override
	public boolean insert(SolrInputDocument solrInputDocument) {
		try {
			//根据Id删除
			solrClient.add(coreName, solrInputDocument);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

	/**
	 * 批量插入
	 * @param solrInputDocuments
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#batchInsertDocument(java.util.Collection)
	 */
	@Override
	public boolean batchInsertDocument(Collection<SolrInputDocument> solrInputDocuments) {
		try {
			//根据Id删除
			solrClient.add(coreName, solrInputDocuments);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}
	
	
	
	//=============================== 更新 ===============================
	
	/**
	 * 根据Id单个更新，Id不存在则不更新
	 * @param record
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#updateById(com.aimspeed.operation.entity.bean.solr.BaseSolrBean)
	 */
	@Override
	public boolean updateById(T record) {
		
		//获取到所有的属性
		Field[] fields = record.getClass().getDeclaredFields();
		if(null != fields && fields.length > 0) {
			SolrInputDocument doc = new SolrInputDocument();
			for (Field field : fields) {
				field.setAccessible(true);
				
				//获取到属性名
				String name = field.getName();
				if(null != name && "serialVersionUID".equals(name.trim())) {
					continue;
				}
				
				
				
				//获取到属性数据
				Object value = null;
				try {
					value = field.get(record);
				} catch (IllegalArgumentException | IllegalAccessException e) {
//					e.printStackTrace();
				}
				
				//ID则直接设置数据
				if(null != name && "id".equals(name.trim())) {
					doc.addField("id",value);
					continue;
				}
				
				//非ID数据则设置为更新
				if(null != value && !"".equals(value.toString())) {
					Map<String,Object> map = new HashMap<>();
					map.put("set",value);
					doc.addField(name, map);
				}
			}
			return insert(doc);
		}
		
		return false;
	}
	

	/**
	 * 批量更新，如果Id不存在则插入（无法保证Id不存在就不更新）
	 * @param records
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#batchUpdateBeanById(java.util.Collection)
	 */
	@Override
	public boolean batchUpdateBeanById(Collection<T> records) {
		for (T record : records) {
			//获取到所有的属性
			Field[] fields = record.getClass().getDeclaredFields();
			if(null != fields && fields.length > 0) {
				SolrInputDocument doc = new SolrInputDocument();
				for (Field field : fields) {
					field.setAccessible(true);
					
					//获取到属性名
					String name = field.getName();
					if(null != name && "serialVersionUID".equals(name.trim())) {
						continue;
					}
					
					
					
					//获取到属性数据
					Object value = null;
					try {
						value = field.get(record);
					} catch (IllegalArgumentException | IllegalAccessException e) {
//						e.printStackTrace();
					}
					
					//ID则直接设置数据
					if(null != name && "id".equals(name.trim())) {
						doc.addField("id",value);
						continue;
					}
					
					//非ID数据则设置为更新
					if(null != value && !"".equals(value.toString())) {
						Map<String,Object> map = new HashMap<>();
						map.put("set",value);
						doc.addField(name, map);
					}
				}
				insert(doc);
			}
		}
		return true;
	}
	
	/**
	 * 通过自由定义属性单个更新，Id不存在则不更新
	 * 例如：SolrInputDocument doc = new SolrInputDocument();
     *		doc.setField("id", "499dc174aec04263801a2181a730265b");
     *		doc.setField("name", "我是中国人AAA, 我爱中国");
	 * @param solrInputDocument
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#updateById(org.apache.solr.common.SolrInputDocument)
	 */
	@Override
	public boolean updateById(SolrInputDocument solrInputDocument) {
		return insert(solrInputDocument);
	}
	
	/**
	 * 批量更新，如果Id不存在则插入（无法保证Id不存在就不更新）
	 * @param solrInputDocuments
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#batchUpdateDocumentById(java.util.Collection)
	 */
	@Override
	public boolean batchUpdateDocumentById(Collection<SolrInputDocument> solrInputDocuments) {
		return batchInsertDocument(solrInputDocuments);
	}
	
	
	//=============================== 删除 ===============================

	/**
	 * 根据多个Id进行删除
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#deleteByIds(java.lang.String[])
	 */
	@Override
	public boolean deleteByIds(String[] ids) {
		try {
			//根据Id删除
			solrClient.deleteById(coreName, Arrays.asList(ids));
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

	/**
	 * 根据多个Id进行删除
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#deleteByIds(java.util.List)
	 */
	@Override
	public boolean deleteByIds(List<String> ids) {
		try {
			//根据Id删除
			solrClient.deleteById(coreName, ids);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

	/**
	 * 根据Solr查询语句进行删除
	 * @param query
	 * @return
	 * @overridden @see com.aimspeed.operation.repository.solr.BaseSolrMapper#deleteByQuery(java.lang.String)
	 */
	@Override
	public boolean deleteByQuery(String query) {
		try {
			//根据Id删除
			solrClient.deleteByQuery(coreName, query);
			solrClient.commit(coreName);
		} catch (SolrServerException | IOException e) {
			//回滚
			try {
				solrClient.rollback(coreName);
			} catch (SolrServerException | IOException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

}
