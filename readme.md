#4 Solr 单机版
solr-7.6.0 为例
    
  1.复制 solr-7.6.0\server\solr\configsets 下的 sample_techproducts_configs 文件 至 solr 文件下 修改文件夹名字 (Core的名称)
  2.修改 core文件夹下conf 中的managed-schema文件:
   (<field name="id" type="string" indexed="true"  stored="true" />  	
    <field name="_version_" type="long" indexed="true"  stored="true" />
  <uniqueKey>id</uniqueKey>
  )
  其中这三个标签必须存在且每一个field 里的type 需要在  fieldType标签中配置 
  3,solr-config基本不用改
  4,启动solr管理页面 solr admin -> addCore 遇到问题多看 管理页面中的logging 中的日志 添加的core名称必须和第一步修改的文件夹名字相同.
  5,添加导入数据 
  在solrconfig.xml文件的后面配置如下信息:
  <!--引入DataImportHandler类的jar-->
  <lib dir="${solr.install.dir:../../../..}/dist/" regex="solr-dataimporthandler-.*\.jar" />
  
  <requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
    <lst name="defaults">
      <str name="config">data-config.xml</str>
    </lst>
  </requestHandler>
 
 编写 data-config.xml是因为这个文件需要managed_schema里面的域与数据库字段进行映射
 <?xml version="1.0" encoding="UTF8"?>
 <dataConfig>
     <dataSource type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://127.0.0.1:3306/db_jx" user="root" password="root" batchSize="-1" />
 　　<document>
         <entity name="mycore_test" query="select id ,vip,point,content,add_time from solr_mycore">
              <!--column的id是数据库的id,name的id是managed_schema里面的id，id是必须，并且唯一的-->
             <field column="id" name="id" />
              <!--column的vip是数据库的vip字段,name的vip是managed_schema里面的vip,下面配置同理-->
             <field column="vip" name="vip" />
             <field column="point" name="point" />
             <field column="content" name="content" />
             <field column="add_time" name="add_time" />
         </entity>  
     </document>
 </dataConfig>
 
 这里的配置表示mycore的数据导入使用solr的DataImportHandler，而这个handler所在的jar位于E:\solr-6.6.0\solr-6.6.0\dist目录里面,解压的时候就有。通过配置lib节点来进行引入  如果reload 报找不到类就将对应的jar 添加至 solr-7.6.0\server\solr-webapp\webapp\WEB-INF\lib 
 
   
 
