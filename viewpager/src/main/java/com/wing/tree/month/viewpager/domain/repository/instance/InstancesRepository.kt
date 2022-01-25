package com.wing.tree.month.viewpager.domain.repository.instance

import com.wing.tree.month.viewpager.data.model.Instance
import kotlinx.coroutines.flow.Flow

interface InstancesRepository {
    fun instances(year: Int, month: Int): Flow<LinkedHashMap<Int, List<Instance>>>
}