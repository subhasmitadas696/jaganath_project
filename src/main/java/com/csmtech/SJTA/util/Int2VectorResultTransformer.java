package com.csmtech.SJTA.util;

import org.hibernate.transform.BasicTransformerAdapter;

public class Int2VectorResultTransformer extends BasicTransformerAdapter {

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		if (tuple.length == 1) {
			return tuple[0];
		}
		return super.transformTuple(tuple, aliases);
	}
}