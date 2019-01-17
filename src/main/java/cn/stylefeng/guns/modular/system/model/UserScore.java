package cn.stylefeng.guns.modular.system.model;

import java.math.BigDecimal;

/**
 * <p>
 * 个人用户分数排名
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public class UserScore  implements  Comparable<UserScore> {


    /**
     * 主键id
     */
	private Integer id;

	private String name;

	private BigDecimal total;

	private BigDecimal variance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getVariance() {
		return variance;
	}

	public void setVariance(BigDecimal variance) {
		this.variance = variance;
	}
	public UserScore(BigDecimal total,BigDecimal variance) {
		super();
		this.total = total;
		this.variance = variance;
	}
	@Override
	public int compareTo(UserScore o) {
		if(this.getTotal().compareTo(o.getTotal())>0)
		{
			return -1;
		}else if(this.getTotal().compareTo(o.getTotal())<0){
			return 1;
		}else{
			if(this.getVariance().compareTo(o.getVariance())>0){
				return 1;
			}else{
				return -1;
			}
		}
	}

}
