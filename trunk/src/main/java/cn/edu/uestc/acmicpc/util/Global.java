/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.db.dao.impl.DepartmentDAO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Global enumerates and constants inside project.
 * <p/>
 * <strong>WARN:</strong> this file may be rewritten carefully.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 4
 */
public class Global {

    @SuppressWarnings("UnusedDeclaration")
    public enum OnlineJudgeReturnType {
        OJ_WAIT("Queuing"), OJ_AC("Accepted"), OJ_PE("Presentation Error on test# $case"),
        OJ_TLE("Time Limit Exceeded on test# $case"), OJ_MLE("Memory Limit Exceeded on test# $case"),
        OJ_WA("Wrong Answer on test# $case"), OJ_OLE("Output Limit Exceeded on test# $case"),
        OJ_CE("Compilation Error"), OJ_RE_SEGV("Runtime Error on test# $case"),
        OJ_RE_FPE("Runtime Error on test# $case"), OJ_RE_BUS("Runtime Error on test# $case"),
        OJ_RE_ABRT("Runtime Error on test# $case"), OJ_RE_UNKNOWN("Runtime Error on test# $case"),
        OJ_RF("Restricted Function on test# $case"), OJ_SE("System Error on test# $case"),
        OJ_RE_JAVA("Runtime Error on test# $case");
        private String description;

        /**
         * Get enumerate value's description.
         *
         * @return description string for specific online judge return type
         */
        public String getDescription() {
            return description;
        }

        private OnlineJudgeReturnType(String description) {
            this.description = description;

        }
    }

    /**
     * User's authentication type(`type` column in user entity).
     */
    public enum AuthenticationType {
        NORMAL("normal user"), ADMIN("administrator"), CONSTANT("constant user");
        private String description;

        private AuthenticationType(String description) {
            this.description = description;
        }

        /**
         * Get enumerate value's description.
         *
         * @return description string for specific authentication type
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Department DAO using for get all departments.
     */
    private DepartmentDAO departmentDAO;

    /**
     * Department list.
     */
    private List<Department> departmentList;

    /**
     * authentication type list
     */
    private List<AuthenticationType> authenticationTypeList;

    /**
     * Initializer.
     */
    public void init() throws AppException {
        this.departmentList = departmentDAO.findAll();
        this.authenticationTypeList = new ArrayList<AuthenticationType>();
        this.authenticationTypeList.clear();
        Collections.addAll(authenticationTypeList, AuthenticationType.values());
    }

    /**
     * Get departmentDAO object from Spring IoC
     *
     * @param departmentDAO struts departmentDAO
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    /**
     * Get all departments in database.
     *
     * @return All departments
     */
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    /**
     * Get all authentications.
     *
     * @return All authentication type
     */
    @SuppressWarnings("UnusedDeclaration")
    public List<AuthenticationType> getAuthenticationTypeList() {
        return authenticationTypeList;
    }
}
