/*
 * Copyright (c) 2008-2017, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.stream.impl.distributed;

import com.hazelcast.jet.Distributed.Comparator;
import com.hazelcast.jet.stream.impl.distributed.DistributedComparators.NullComparator;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static com.hazelcast.jet.Distributed.Comparator.nullsFirst;
import static com.hazelcast.jet.Distributed.Comparator.nullsLast;
import static com.hazelcast.jet.stream.impl.distributed.DistributedComparators.NATURAL_ORDER_COMPARATOR;
import static com.hazelcast.jet.stream.impl.distributed.DistributedComparators.REVERSE_ORDER_COMPARATOR;
import static org.junit.Assert.*;

@Category(QuickTest.class)
@RunWith(HazelcastParallelClassRunner.class)
public class DistributedComparatorsTest {

    @Test
    public void reverseComparator() {
        assertSame(REVERSE_ORDER_COMPARATOR, NATURAL_ORDER_COMPARATOR.reversed());
        assertSame(NATURAL_ORDER_COMPARATOR, REVERSE_ORDER_COMPARATOR.reversed());
    }

    @Test
    public void reverseOrderComparator() {
        Comparator c = REVERSE_ORDER_COMPARATOR;
        assertEquals(1, c.compare(1, 2));
        assertEquals(-1, c.compare(2, 1));
    }

    @Test
    public void nullsFirstComparator() {
        Comparator c = nullsFirst(NATURAL_ORDER_COMPARATOR);
        assertEquals(-1, c.compare(1, 2));
        assertEquals(1, c.compare(2, 1));
        assertEquals(1, c.compare(0, null));
        assertEquals(-1, c.compare(null, 0));
    }

    @Test
    public void nullsLastComparator() {
        Comparator c = nullsLast(NATURAL_ORDER_COMPARATOR);
        assertEquals(-1, c.compare(1, 2));
        assertEquals(1, c.compare(2, 1));
        assertEquals(-1, c.compare(0, null));
        assertEquals(1, c.compare(null, 0));
    }

    @Test
    public void nullsFirst_withoutWrapped() {
        Comparator c = nullsFirst(null);
        assertEquals(0, c.compare(1, 2));
        assertEquals(0, c.compare(2, 1));
        assertEquals(1, c.compare(0, null));
        assertEquals(-1, c.compare(null, 0));
    }

    @Test
    public void nullsLast_withoutWrapped() {
        Comparator c = nullsLast(null);
        assertEquals(0, c.compare(1, 2));
        assertEquals(0, c.compare(2, 1));
        assertEquals(-1, c.compare(0, null));
        assertEquals(1, c.compare(null, 0));
    }
}