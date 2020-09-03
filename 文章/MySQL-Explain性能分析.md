## 0.简述

> **含义:** EXPLAIN语句是用来帮助我们查看某个**查询语句**( update\insert\delete也可以添加 )的具体**执⾏计划**

> **能干什么:** 
>
> - 表的读取顺序
> - 哪些索引可以使用
> - 数据读取操作的操作类型
> - 哪些索引被实际使用
> - 表之间的引用
> - 每张表有多少行被优化器查询

```sql
-- 执行sql
EXPLAIN SELECT 1;

--- 结果

| id | select_type | table | partitions | type | possible_keys | key | key_len | ref | rows | filtered | Extra |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
| 1  |    SIMPLE   |  NULL |    NULL    | NULL |     NULL      | NULL |  NULL  | NULL | NULL |   NULL  | No	tables	used |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------+
```

> 对于查询出来的执行计划, 每一个参数所代表的**含义**是什么? 每个参数具体有哪些**取值**? 下面进行简单描述

## 1.参数详解

#### 1.1参数概览

|    列  名     |                            描  述                            |
| :-----------: | :----------------------------------------------------------: |
|      id       | select查询的序列号,包含一组数字; 表示**查询**中执行select子句或操作表的**顺序** |
|  select_type  |               SELECT关键字对应的那个查询的类型               |
|     table     |                             表名                             |
|  partitions   |                        匹配的分区信息                        |
|     type      |                      针对单表的访问⽅法                      |
| possible_keys |                        可能⽤到的索引                        |
|      key      |                       实际上使⽤的索引                       |
|    key_len    |                     实际使⽤到的索引⻓度                     |
|      ref      |    当使⽤索引列等值查询时，与索引列进⾏等值匹配的对象信息    |
|     rows      |                   预估的需要读取的记录条数                   |
|   filtered    |         某个表经过搜索条件过滤后剩余记录条数的百分⽐         |
|     Extra     |                        ⼀些额外的信息                        |



#### 1.2参数详解

**建表SQL**

```sql
CREATE TABLE `s1` (
  `s1_id` int(11) NOT NULL,
  PRIMARY KEY (`s1_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `s2` (
  `s2_id` int(11) NOT NULL,
  PRIMARY KEY (`s2_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

##### <font style="color:red;">1.2.1 id</font >

>select查询的序列号,包含一组数字; 表示**查询**中执行select子句或操作表的**顺序**
>
>我这里用大白话讲就是**sql语句中每个select语句的查询顺序**

**取值**

>1. id相同;  执行顺序由上至下
>
>   ```sql
>   mysql> EXPLAIN	SELECT	*	FROM	s1	INNER	JOIN	s2;
>   
>   +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+---------------+
>   | id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra        |
>   +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+---------------+
>   |  1 | SIMPLE      | s1    | NULL       | index | NULL          | PRIMARY | 4       | NULL |    1 |   100.00 | Using index   |
>   |  1 | SIMPLE      | s2    | NULL       | index | NULL          | PRIMARY | 4       | NULL |    1 |   100.00 | Using index; Using join buffer (Block Nested Loop) |
>   +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+---------------+
>   ```
>
>2. id不同;  如果是子查询，id的序号会递增，id值越大优先级越高，越先被执行
>
>   ```sql
>   mysql> EXPLAIN	SELECT	*	FROM	s1	WHERE	s1_id	IN	(SELECT	s2_id	FROM	s2)	OR	s1_id	=	'a'; 
>   
>   +----+--------------------+-------+------------+-----------------+---------------+---------+---------+------+------+---------+
>   | id | select_type        | table | partitions | type            | possible_keys | key     | key_len | ref  | rows | filtered | Extra                    |
>   +----+--------------------+-------+------------+-----------------+---------------+---------+---------+------+------+---------+
>   |  1 | PRIMARY            | s1    | NULL       | index           | PRIMARY       | PRIMARY | 4       | NULL |    1 |   100.00 | Using where; Using index |
>   |  2 | DEPENDENT SUBQUERY | s2    | NULL       | unique_subquery | PRIMARY       | PRIMARY | 4       | func |    1 |   100.00 | Using index              |
>   +----+--------------------+-------+------------+-----------------+---------------+---------+---------+------+------+---------+
>   ```
>
>3. id相同不同，同时存在

##### 1.2.2 select_type

> 查询的类型，主要是用于区别普通查询、联合查询、子查询等的复杂查询

**取值**

>**SIMPLE:**  简单的 select 查询,查询中不包含子查询或者UNION
>
>```sql
>mysql> EXPLAIN	SELECT * FROM s1;
>
>+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
>| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra       |
>+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
>|  1 | SIMPLE      | s1    | NULL       | index | NULL          | PRIMARY | 4       | NULL |    1 |   100.00 | Using index |
>+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
>```
>
>---
>
>**PRIMARY:**  对于包含UNION、UNION ALL或者⼦查询的⼤查询来说，它是由⼏个⼩查询组成的，最外层或最左边的查询则被标记为Primary
>
>**UNION:**  对于包含UNION或者UNION ALL的⼤查询来说，它是由⼏个⼩查询组成的，其中除了最外层或最左边的查询的那个⼩查询以外，其余的⼩查询的select_type值 就是 UNION
>
>**UNION RESULT:**  从UNION表获取结果的SELECT
>
>**DERIVED:**  在FROM列表中包含的子查询被标记为DERIVED(衍生)MySQL会递归执行这些子查询, 把结果放在临时表里。
>
>**SUBQUERY:**  在SELECT或WHERE列表中包含了子查询
>
>**DEPENDENT SUBQUERY:**  在SELECT或WHERE列表中包含了子查询,子查询基于外层
>
>**UNCACHEABLE SUBQUREY:**  无法被缓存的子查询
>
>

##### 1.2.3 table

> 显示这一行的数据是关于哪张表的



> ```sql
> mysql> EXPLAIN	SELECT * FROM s1;
> 
> +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
> | id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra       |
> +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
> |  1 | SIMPLE      | s1    | NULL       | index | NULL          | PRIMARY | 4       | NULL |    1 |   100.00 | Using index |
> +----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+-------------+
> ```

##### <font style="color:red;">1.2.4 type</font>

>显示查询使用了何种类型
>
>从最好到最差依次是：
>`system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range(尽量保证) > index > ALL`
>
>**一般来说，得保证查询至少达到range级别，最好能达到ref。**

**取值**( 具体例子可以参考: <<MySQL 是怎样运行的：从根儿上理解 MySQL>>中第15章节 )

>**system:**  表只有一行记录（等于系统表），这是const类型的特列，平时不会出现，这个也可以忽略不计
>
>**const:  **当我们根据主键或者唯⼀⼆级索引列与常数进⾏等值匹配时，对单表的访问⽅法就是const
>
>**eq_ref:  **在连接查询时，如果被驱动表是通过主键或者唯⼀⼆级索引列等值匹配的⽅式进⾏访问的（如果该主键或者唯⼀⼆级索引是联合索引的话，所有的索引列都必须进⾏等值⽐较），则对该被驱动表的访问⽅法就是eq_ref
>
>**ref:**  当通过普通的⼆级索引列与常量进⾏等值匹配时来查询某个表，那么对该表的访问⽅法就可能是ref
>
>**fulltext:  **全⽂索引，跳过～ 
>
>**ref_or_null :  **当对普通⼆级索引进⾏等值匹配查询，该索引列的值也可以是NULL值时，那么对该表的访问⽅法就可能是ref_or_null
>
>**index_merge:  **在查询过程中需要多个索引组合使用，通常出现在有 or 的关键字的sql中
>
>**unique_subquery:  **该联接类型类似于index_subquery。 子查询中的唯一索引
>
>**index_subquery:  **利用索引来关联子查询，不再全表扫描。
>
><font style="color:yellow;"><strong>range:  </strong></font>只检索给定范围的行,使用一个索引来选择行。key 列显示使用了哪个索引,  一般就是在你的where语句中出现了between、<、>、in等的查询,  这种范围扫描索引扫描比全表扫描要好，因为它只需要开始于索引的某一点，而结束语另一点，不用扫描全部索引。
>
><font style="color:#FF6633;"><strong>index:  </strong></font>index与ALL区别为index类型只遍历索引树。这通常比ALL快，因为索引文件通常比数据文件小。（也就是说虽然**all和Index都是读全表**，但index是从索引中读取的，而all是从硬盘中读的）
>
><font style="color:red;"><strong>all:  </strong></font>将遍历全表以找到匹配的行

##### <font style="color:red;">1.2.5 possible_keys</font>

> 显示可能应用在这张表中的索引，一个或多个。
>
> 查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用

##### <font style="color:red;">1.2.6 key</font>

> 实际使用的索引。如果为NULL，则没有使用索引
>
> 查询中若使用了覆盖索引，则该索引和查询的select字段重叠

##### <font style="color:red;">1.2.7 key_len</font>

> 表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。 
>
> key_len字段能够帮你**检查是否充分的利用上了索引**

##### <font style="color:red;">1.2.8 ref</font>

>**显示索引的哪一列被使用了**，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值

##### <font style="color:red;">1.2.9 rows</font>

> rows列显示MySQL认为它执行查询时必须检查的行数。

##### 1.2.10 Extra

> 包含不适合在其他列中显示但十分重要的额外信息

**取值**

>**Null:** 说明有回表操作
>
><font style="color:red;"><strong>Using filesort:  </strong></font>说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。**MySQL中无法利用索引完成的排序操作称为“文件排序”**
>
><font style="color:red;"><strong>Using temporary:  </strong></font>使了用临时表保存中间结果,MySQL在对查询结果排序时使用临时表。常见于排序 order by 和分组查询 group by。
>
><font style="color:#FF6633;"><strong>Using index:  </strong></font>表示相应的select操作中使用了覆盖索引(Covering Index)，避免访问了表的数据行，效率不错！
>如果同时出现using where，表明索引被用来执行索引键值的查找;
>如果没有同时出现using where，表明索引只是用来读取数据而非利用索引执行查找。
>
><font style="color:red;"><strong>Using join buffer:  </strong></font>使用了连接缓存
>
>**Using where:  **表明使用了where过滤
>
>**impossible where:  **where子句的值总是false，不能用来获取任何元组
>
>**select tables optimized away: **在没有GROUPBY子句的情况下，基于索引优化MIN/MAX操作或者
>对于MyISAM存储引擎优化COUNT(*)操作，不必等到执行阶段再进行计算，
>查询执行计划生成的阶段即完成优化。

## 2.参考文献

<<MySQL 是怎样运行的：从根儿上理解 MySQL>>中第15\16章节

尚硅谷MySQL高级部分 索引优化分析->性能分析->Explain	