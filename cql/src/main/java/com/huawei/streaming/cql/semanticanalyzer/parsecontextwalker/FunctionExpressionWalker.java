/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.streaming.cql.semanticanalyzer.parsecontextwalker;

import java.util.List;

import com.google.common.collect.Lists;
import com.huawei.streaming.cql.DriverContext;
import com.huawei.streaming.cql.executor.FunctionInfo;
import com.huawei.streaming.cql.executor.FunctionRegistry;
import com.huawei.streaming.cql.executor.FunctionType;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.FunctionContext;
import com.huawei.streaming.cql.semanticanalyzer.parser.context.ParseContext;

/**
 * 函数表达式遍历
 * 
 */
public class FunctionExpressionWalker implements ParseContextWalker
{
    
    private List<FunctionContext> functions;
    
    /**
     * <默认构造函数>
     */
    public FunctionExpressionWalker()
    {
        functions = Lists.newArrayList();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean walk(ParseContext parseContext)
    {
        if (parseContext instanceof FunctionContext)
        {
            functions.add((FunctionContext)parseContext);
            return true;
        }
        return false;
    }
    
    /**
     * 是否包含udaf聚合函数
     */
    public boolean isContainsUDAF()
    {
        for (FunctionContext function : functions)
        {
            FunctionRegistry funRegistry = DriverContext.getFunctions().get();
            FunctionInfo functionInfo = funRegistry.getFunctionInfoByFunctionName(function.getName());
            if (functionInfo.getType() == FunctionType.UDAF)
            {
                return true;
            }
        }
        return false;
    }
    
}
