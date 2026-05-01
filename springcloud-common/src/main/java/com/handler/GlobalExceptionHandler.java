package com.handler;

import com.utils.R;
import javax.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器 - 统一返回格式化错误信息
 *
 * @author abo
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return R.error(String.format("请求地址 %s 不支持 %s 请求方式，请使用 %s",
                requestURI, e.getMethod(), String.join(",", e.getSupportedMethods()))).put("errorMsg", e);
    }

    /**
     * 参数验证失败异常（如 @Valid 校验）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.error(String.format("请求地址 %s 参数校验失败：%s", requestURI, message)).put("errorMsg", e);
    }

    /**
     * SQL语法错误（如字段名拼写错误）
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace(); // 保留日志便于排查
        return R.error(String.format("请求地址 %s 发生SQL语法错误：%s", requestURI, errorMessage)).put("errorMsg", e);
    }

    /**
     * 数据完整性约束违反（如唯一键冲突、外键约束、非空字段为空）
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public R handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace();
        return R.error(String.format("请求地址 %s 数据操作异常：%s", requestURI, errorMessage)).put("errorMsg", e);
    }

    /**
     * 其他未分类的SQL异常（连接、权限、跨表操作等）
     */
    @ExceptionHandler(UncategorizedSQLException.class)
    public R handleUncategorizedSqlException(UncategorizedSQLException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String causeMessage = e.getMostSpecificCause().getMessage();
        String errorMessage = DatabaseErrorParser.parseDatabaseErrorMessage(e);
        e.printStackTrace();
        return R.error(String.format("请求地址 %s 数据库异常：%s", requestURI, errorMessage))
                .put("remark", "请检查跨表关联或数据库权限设置").put("errorMsg", e);
    }

    /**
     * 拦截未知运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        e.printStackTrace();
        return R.error(String.format("请求地址 %s 发生运行时异常：%s", requestURI, e.getMessage())).put("errorMsg", e);
    }

    /**
     * 拦截所有其他异常（兜底处理）
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        e.printStackTrace();
        return R.error(String.format("请求地址 %s 发生系统异常：%s", requestURI, e.getMessage())).put("errorMsg", e);
    }


    /**
     * 数据库异常信息解析器
     * 用于解析数据库异常中的具体错误信息，如表名、字段名、约束名等
     *
     * @author abo
     */
    public static class DatabaseErrorParser {

        /**
         * 解析数据库异常信息，返回用户友好的错误描述
         *
         * @param e 数据库相关异常
         * @return 友好的错误信息
         */
        public static String parseDatabaseErrorMessage(Exception e) {
            String message = e.getMessage();

            if (e instanceof DuplicateKeyException) {
                return parseDuplicateKeyError(message);
            } else if (e instanceof DataIntegrityViolationException) {
                return parseDataIntegrityError(message);
            } else if (e instanceof BadSqlGrammarException) {
                return parseSQLError(message);
            } else if (e instanceof UncategorizedSQLException) {
                return parseUncategorizedSqlError(((UncategorizedSQLException) e).getMostSpecificCause().getMessage());
            }

            return "数据库操作异常：" + e.getClass().getSimpleName();
        }

        /**
         * 解析重复键错误信息
         */
        private static String parseDuplicateKeyError(String message) {
            if (message == null) return "数据重复错误";
            Pattern duplicatePattern = Pattern.compile("Duplicate entry '([^']+)' for key '([^']+)'");
            Matcher matcher = duplicatePattern.matcher(message);

            if (matcher.find()) {
                String duplicateValue = matcher.group(1);
                String keyInfo = matcher.group(2);

                // 尝试解析表名和字段名
                String tableName = extractTableNameFromKey(keyInfo);
                String fieldName = extractFieldNameFromKey(keyInfo);

                if (tableName != null && fieldName != null) {
                    return String.format("数据重复错误：表[%s]中字段[%s]的值'%s'已存在", tableName, fieldName, duplicateValue);
                } else {
                    return String.format("数据重复错误：值'%s'违反了唯一约束'%s'", duplicateValue, keyInfo);
                }
            }

            return "数据重复错误：违反了唯一性约束";
        }

        /**
         * 解析数据完整性错误信息
         */
        private static String parseDataIntegrityError(String message) {
            if (message == null) return "数据完整性错误";

            // 解析外键约束错误
            if (message.toLowerCase().contains("foreign key constraint fails")) {
                String foreignKeyInfo = extractForeignKeyInfo(message);
                if (foreignKeyInfo != null) {
                    return "外键约束错误：" + foreignKeyInfo;
                }
                return "外键约束错误：引用的数据不存在或已被删除";
            }

            // 解析NOT NULL约束错误
            if (message.contains("cannot be null") || message.contains("Field")) {
                String tableFieldInfo = extractTableFieldInfo(message);
                if (tableFieldInfo != null) {
                    return "必填字段错误：" + tableFieldInfo + " 字段不能为空";
                }
                return "必填字段错误：存在未填写的必填项";
            }

            // 解析字段长度错误
            // Data truncation: Data too long for column 'token'
            if (message.contains("Data too long for column")) {
                String[] tableAndColumn = extractTableNameAndColumnFromLengthError(message);
                String tableName = tableAndColumn[0];
                String columnName = tableAndColumn[1];

                if (tableName != null && columnName != null) {
                    return String.format("字段长度超限错误：表[%s]中字段[%s]的值超出最大允许长度，请检查输入数据", tableName, columnName);
                } else if (columnName != null) {
                    return String.format("字段长度超限错误：字段[%s]的值超出最大允许长度，请检查输入数据", columnName);
                }
                return "字段长度超限错误：某个字段的值超出最大允许长度，请检查输入数据";
            }


            // 尝试解析为重复键错误（有时DataIntegrityViolationException也会包含重复键信息）
            return parseDuplicateKeyError(message);
        }

        /**
         * 解析SQL语法错误信息
         */
        private static String parseSQLError(String message) {
            if (message == null) return "SQL语法错误";

            // 解析未知列错误
            if (message.contains("Unknown column")) {
                String columnInfo = extractColumnInfo(message);
                String tableName = extractTableNameFromColumnError(message);

                if (columnInfo != null && tableName != null) {
                    return String.format("字段不存在：表[%s]中不存在字段[%s]", tableName, columnInfo);
                } else if (columnInfo != null) {
                    return "字段不存在：" + columnInfo;
                }
            }

            // 解析表不存在错误
            if (message.contains("Table") && message.contains("doesn't exist")) {
                String tableName = extractTableName(message);
                if (tableName != null) {
                    return "表不存在：" + tableName;
                }
                return "数据表不存在：请检查表名是否正确";
            }

            // 解析视图不存在错误
            if (message.contains("View") && message.contains("doesn't exist")) {
                String viewName = extractViewName(message);
                if (viewName != null) {
                    return "视图不存在：" + viewName;
                }
                return "数据库视图不存在：请检查视图名是否正确";
            }

            // 解析存储过程或函数不存在错误
            if (message.contains("PROCEDURE") && message.contains("does not exist")) {
                String procedureName = extractProcedureName(message);
                if (procedureName != null) {
                    return "存储过程不存在：" + procedureName;
                }
                return "存储过程不存在：请检查存储过程名是否正确";
            }

            // 解析语法错误
            if (message.contains("You have an error in your SQL syntax")) {
                return "SQL语法错误：SQL语句语法不正确，请检查SQL语法";
            }

            return "SQL语法错误：请检查SQL语句语法";
        }

        /**
         * 解析未分类的SQL异常信息
         */
        private static String parseUncategorizedSqlError(String message) {
            if (message == null) return "数据库操作异常";

            // 解析权限错误
            if (message.contains("Access denied")) {
                return "数据库权限错误：当前用户没有执行此操作的权限";
            }

            // 解析连接错误
            if (message.contains("Communications link failure") ||
                    message.contains("Connection refused") ||
                    message.contains("No suitable driver found")) {
                return "数据库连接错误：无法连接到数据库服务器";
            }

            // 调用SQL语法错误解析作为备选
            return parseSQLError(message);
        }

        // --- 辅助解析方法 ---

        /**
         * 从列错误信息中提取列名
         */
        private static String extractColumnInfo(String message) {
            Pattern pattern = Pattern.compile("Unknown column '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从字段长度错误信息中提取表名和字段名
         *
         * @param message 错误信息字符串
         * @return 包含表名和字段名的数组，格式为 [tableName, columnName]
         */
        private static String[] extractTableNameAndColumnFromLengthError(String message) {
            // 正则匹配格式：Data truncation: Data too long for column 'column_name' at row 1 in statement [INSERT INTO table_name ...]
            Pattern pattern = Pattern.compile(
                    "Data too long for column '([^']+)'[\\s\\S]*?INSERT INTO\\s+([^\\s(]+)"
            );
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String columnName = matcher.group(1);
                String tableName = matcher.group(2);
                return new String[]{tableName, columnName};
            }

            // 如果无法匹配完整格式，则尝试仅提取字段名
            String columnName = extractColumnNameFromLengthError(message);
            return new String[]{null, columnName};
        }

        /**
         * 从列错误信息中提取表名
         */
        private static String extractTableNameFromColumnError(String message) {
            Pattern pattern = Pattern.compile("Unknown column '[^']+' in '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从表错误信息中提取表名
         */
        private static String extractTableName(String message) {
            Pattern pattern = Pattern.compile("Table '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从视图错误信息中提取视图名
         */
        private static String extractViewName(String message) {
            Pattern pattern = Pattern.compile("View '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从字段长度错误信息中提取字段名
         */
        private static String extractColumnNameFromLengthError(String message) {
            Pattern pattern = Pattern.compile("Data too long for column '([^']+)'");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从存储过程错误信息中提取过程名
         */
        private static String extractProcedureName(String message) {
            Pattern pattern = Pattern.compile("PROCEDURE ([^\\s]+)");
            Matcher matcher = pattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 从错误信息中提取表字段信息
         */
        private static String extractTableFieldInfo(String message) {
            // 解析类似 "Field 'field_name' doesn't have a default value" 或 "cannot be null"
            Pattern fieldPattern = Pattern.compile("Field '([^']+)'");
            Matcher matcher = fieldPattern.matcher(message);
            return matcher.find() ? matcher.group(1) : null;
        }

        /**
         * 解析外键约束详细信息
         */
        private static String extractForeignKeyInfo(String message) {
            // 解析外键约束格式：CONSTRAINT `constraint_name` FOREIGN KEY (`child_field`) REFERENCES `parent_table` (`parent_field`)
            Pattern pattern = Pattern.compile(
                    "CONSTRAINT `([^`]+)` FOREIGN KEY \\(`([^`]+)`\\) REFERENCES `([^`]+)` \\(`([^`]+)`\\)"
            );
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String constraintName = matcher.group(1);
                String childField = matcher.group(2);
                String parentTable = matcher.group(3);
                String parentField = matcher.group(4);

                return String.format("约束[%s]：子表字段[%s]引用父表[%s]的字段[%s]不存在",
                        constraintName, childField, parentTable, parentField);
            }
            return null;
        }

        /**
         * 从键信息中提取表名（如 table.key_name -> table）
         */
        private static String extractTableNameFromKey(String keyInfo) {
            if (keyInfo != null && keyInfo.contains(".")) {
                return keyInfo.split("\\.")[0];
            }
            return null;
        }

        /**
         * 从键信息中提取字段名（如 table.field_UNIQUE -> field_UNIQUE 或 field）
         */
        private static String extractFieldNameFromKey(String keyInfo) {
            if (keyInfo != null && keyInfo.contains(".")) {
                String fieldName = keyInfo.split("\\.")[1];
                // 如果是 UNIQUE 索引名，去除后缀获取原始字段名
                if (fieldName.endsWith("_UNIQUE") || fieldName.endsWith("_UK")) {
                    return fieldName.substring(0, fieldName.lastIndexOf('_'));
                }
                return fieldName;
            }
            return keyInfo;
        }
    }


}
