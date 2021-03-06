/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.server.namenode;

import java.io.IOException;

import org.apache.hadoop.hdfs.server.namenode.NNStorageRetentionManager.StoragePurger;
import org.apache.hadoop.hdfs.server.protocol.NamenodeRegistration;

/**
 * A JournalManager implementation that uses RPCs to log transactions
 * to a BackupNode.
 */
class BackupJournalManager implements JournalManager {

  private final NamenodeRegistration nnReg;
  private final NamenodeRegistration bnReg;
  
  BackupJournalManager(NamenodeRegistration bnReg,
      NamenodeRegistration nnReg) {
    this.bnReg = bnReg;
    this.nnReg = nnReg;
  }

  @Override
  public EditLogOutputStream startLogSegment(long txId) throws IOException {
    EditLogBackupOutputStream stm = new EditLogBackupOutputStream(bnReg, nnReg);
    stm.startLogSegment(txId);
    return stm;
  }

  @Override
  public void finalizeLogSegment(long firstTxId, long lastTxId)
      throws IOException {
  }

  @Override
  public void setOutputBufferCapacity(int size) {
  }

  @Override
  public void purgeLogsOlderThan(long minTxIdToKeep)
      throws IOException {
  }

  @Override
  public long getNumberOfTransactions(long fromTxnId) 
      throws IOException, CorruptionException {
    // This JournalManager is never used for input. Therefore it cannot
    // return any transactions
    return 0;
  }
  
  @Override
  public EditLogInputStream getInputStream(long fromTxnId) throws IOException {
    // This JournalManager is never used for input. Therefore it cannot
    // return any transactions
    throw new IOException("Unsupported operation");
  }

  @Override
  public void recoverUnfinalizedSegments() throws IOException {
  }

  public boolean matchesRegistration(NamenodeRegistration bnReg) {
    return bnReg.getAddress().equals(this.bnReg.getAddress());
  }

  @Override
  public String toString() {
    return "BackupJournalManager";
  }
}
