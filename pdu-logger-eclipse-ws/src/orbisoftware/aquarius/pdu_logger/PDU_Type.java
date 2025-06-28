/*
 *  Aquarius DIS PDU Suite
 *
 *  Copyright (C) 2011 Harlan Murphy
 *  Orbis Software - orbisoftware@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package orbisoftware.aquarius.pdu_logger;

//PDU types. Use config_.py and config_DISv7.py scripts to change current configuration.

public enum PDU_Type {

   Other_Log_All_PDU_Types(0), 
   Entity_State(1), 
   Fire(2), 
   Detonation(3), 
   Collision(4), 
   Service_Request(5), 
   Resupply_Offer(6), 
   Resupply_Received(7), 
   Resupply_Cancel(8), 
   Repair_Complete(9), 
   Repair_Response(10), 
   Create_Entity(11), 
   Remove_Entity(12), 
   Start_Resume(13), 
   Stop_Freeze(14), 
   Acknowledge(15), 
   Action_Request(16), 
   Action_Response(17), 
   Data_Query(18), 
   Set_Data(19), 
   Data(20), 
   Event_Report(21), 
   Comment(22), 
   Electromagnetic_Emission(23), 
   Designator(24), 
   Transmitter(25), 
   Signal(26), 
   Receiver(27);

   private int value;

   PDU_Type() {
      this.value = this.ordinal();
   }

   PDU_Type(int value) {
      this.value = this.ordinal();
   }

   public int value() {
      return value;
   }
}
