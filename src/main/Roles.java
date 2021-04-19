package main;

/**
 * <h1>Roles</h1>
 * <p>
 *     Enum class containing all the available roles for the users. These roles are used in the Session class in order
 *     to determine what features a given user is allowed to access.
 * </p>
 *
 * @author Arif Hasan
 * @version 1.0
 * @since 03/19/21
 */
public enum Roles {
    OWNER, ADMINISTRATOR, INVENTORY_MANAGER, PURCHASER, ACCOUNTANT, SALES_PERSON
}
