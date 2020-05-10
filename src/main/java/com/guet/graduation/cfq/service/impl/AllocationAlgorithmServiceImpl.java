package com.guet.graduation.cfq.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guet.graduation.cfq.dao.ApplyForUseMapper;
import com.guet.graduation.cfq.entity.ApplyForUse;
import com.guet.graduation.cfq.service.AllocationAlgorithmService;
import com.guet.graduation.cfq.util.SymmetricEncoder;

@Service
public class AllocationAlgorithmServiceImpl implements AllocationAlgorithmService{
	
	@Autowired
	private ApplyForUseMapper applyForUseMapper;

	@Override
	public void Allocation(HttpSession session, Map<String, Object> map) {
		String userId=(String) session.getAttribute("userId");
		//获取所有的属于该用户的设备的使用申请
		List<ApplyForUse> applyForUses=applyForUseMapper.getAllUseApplicationByUserId(userId);
		//处理获得的信息（计算总价和用户名解密）
		for(ApplyForUse applyForUse : applyForUses) {
			//计算总价
			long startTime=applyForUse.getStar_time().getTime();
			long endTime=applyForUse.getEnd_time().getTime();
			long milsecond=(endTime-startTime)/1800000;
			Float totalPrice=milsecond*applyForUse.getPrice();
			applyForUse.setTotalPrice(totalPrice);
			//用户名解密
			String login=SymmetricEncoder.AESDncode(applyForUse.getLogin());
			applyForUse.setLogin(login);
		}
		
		//按照设备id和时间分组——得到的结果为数组，每个数组对应一个设备一天的所有的待处理申请信息，数组里面是Map<String,List>结构，
		Map<String, List<ApplyForUse>> result=applyForUses.stream().collect(Collectors.groupingBy(e->fetchGroupKey(e)));
		//对时间进行处理，得到每个分组中的最优解
		getMaxIntervalSchdeule(result);
		//将所有分组保存在map中，前端遍历显示
		map.put("bestResult", result);
	}
	
	/**
	 * 对时间进行处理，得到每个分组中的最优解
	 * @param result
	 */
	private void getMaxIntervalSchdeule(Map<String, List<ApplyForUse>> result) {
		//遍历分组，得到最优解的结果，并将标志位改变
		for(List<ApplyForUse> applyForUses : result.values()) {
			//此时的状态是一个分组——即某设备某天的所有时刻
			//将该分组利用贪心算法处理，修改其最优解的标志位
			GreedyAlgorithm(applyForUses);
		}
	}

	/**
	 * 贪心算法处理分组
	 * @param applyForUses
	 */
	private void GreedyAlgorithm(List<ApplyForUse> applyForUses) {
		//最优列表
		List<ApplyForUse> bestList=new ArrayList<ApplyForUse>();
		//对传入的列表按照结束时间排序
		sortByEndTime(applyForUses);
		//所有列表的当前位置的标志，用于修改 当其为最佳的时候的flag
		int index=0;
		//最佳列表的前一个位置的标志位，用于与前一个的结束时间比较的时候使用
		int bestIndex=0;
		for(ApplyForUse applyForUse : applyForUses) {
			//加入第一个最优时间——排序后的第一个，即最早结束的时间
			if(bestList.size()==0) {
				//加入最优列表
				bestList.add(applyForUse);
				//修改标志位
				applyForUses.get(index).setFlag(1);
			}else {
				index=index+1;
				//当列表里面存在时间的时候，继续选择最优时间——结束最早且开始时间与最优列表内前一个的结束时间不冲突的
				if(applyForUse.getStar_time().getTime() >= bestList.get(bestIndex).getEnd_time().getTime()) {
					//加入最优列表
					bestList.add(applyForUse);
					//修改当前申请的标志位
					applyForUses.get(index).setFlag(1);
					//修改最佳列表的前一个位置的标志位
					bestIndex=bestIndex+1;
				}
			}
		}
		//输出最佳列表
		System.out.println("最佳列表："+bestList);
	}

	/**
	 * 对传入的列表按照结束时间的先后进行排序
	 * @param applyForUses
	 */
	private void sortByEndTime(List<ApplyForUse> applyForUses) {
		for(int i=0;i<applyForUses.size()-1;i++) {
			for(int j=0;j<applyForUses.size()-1-i;j++) {
				long firstEndTime=applyForUses.get(j).getEnd_time().getTime();
				long nextEndTime=applyForUses.get(j+1).getEnd_time().getTime();
				if(firstEndTime > nextEndTime) {
					ApplyForUse tempApplyForUse=applyForUses.get(j);
					applyForUses.set(j, applyForUses.get(j+1));
					applyForUses.set(j+1, tempApplyForUse);
				}	
			}
		}
	}

	/**
	 * 组合分组信息
	 * @param applyForUse
	 * @return
	 */
	private static String fetchGroupKey(ApplyForUse applyForUse){
	    return applyForUse.getEquipment_id() +"#"+ applyForUse.getUseTime();
	}

}
