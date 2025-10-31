# How to Run Panelara_Exportation_1 in NetBeans

## Prerequisites Checklist

Before running the project, ensure you have:

- [ ] **NetBeans IDE** installed (version 12+ recommended)
- [ ] **Java JDK 16** or higher installed
- [ ] **MySQL Server** running on port 3308
- [ ] **Database** `panelera_exportation_1` created (or `panelera_exportation` if reusing existing)
- [ ] **config.properties** file in project root with correct database credentials

---

## Step 1: Verify MySQL Database is Running

Open Terminal and check if MySQL is running:

```bash
# Check if MySQL is running
mysql -u root -p -h localhost -P 3308 -e "SHOW DATABASES;"
```

If MySQL is not running, start it:
```bash
# On macOS with Homebrew
brew services start mysql

# Or use MySQL preference pane in System Preferences
```

---

## Step 2: Open the Project in NetBeans

### Option A: If Project is Already Open
1. If you see **Panelara_Exportation** (old name) in the Projects panel:
   - Right-click on it → **Close**
2. Go to **File → Open Project**
3. Navigate to: `/Users/mac/NetBeansProjects/Panelara_Exportation_1`
4. Click **Open Project**

### Option B: If Starting NetBeans Fresh
1. Launch **NetBeans IDE**
2. Click **File → Open Project** (or press `Cmd+Shift+O` on Mac)
3. Navigate to: `/Users/mac/NetBeansProjects/`
4. Select **Panelara_Exportation_1** folder
5. Click **Open Project**

You should now see **Panelara_Exportation_1** in the Projects panel.

---

## Step 3: Verify Project Configuration

### Check Libraries
1. In the Projects panel, expand **Panelara_Exportation_1**
2. Expand **Libraries** folder
3. Verify these JAR files are present:
   - ✓ `mysql-connector-java-8.0.20.jar`
   - ✓ `jcalendar-1.4.jar` (or 1.1.4)
   - ✓ `commons-codec-1.6.jar`
   - ✓ `AbsoluteLayout.jar`

**If libraries are missing** (red icons):
1. Right-click **Libraries** → **Add JAR/Folder**
2. Navigate to the JAR file location (check `nbproject/project.properties` for paths)
3. Add the missing JARs

### Check config.properties
1. In the Files panel (not Projects), navigate to project root
2. Double-click **config.properties**
3. Verify settings:
   ```properties
   db.host=localhost
   db.port=3308
   db.name=panelera_exportation_1  # Or your database name
   db.user=root
   db.password=                     # Add your MySQL password if needed
   ```

---

## Step 4: Clean and Build

1. Right-click on **Panelara_Exportation_1** in the Projects panel
2. Select **Clean and Build** (or press `Shift+F11`)
3. Watch the Output window (bottom panel)

**Expected Output:**
```
Building jar: /Users/mac/NetBeansProjects/Panelara_Exportation_1/dist/Panelara_Exportation_1.jar
BUILD SUCCESSFUL (total time: X seconds)
```

**If build fails:**
- Check the Output window for error messages
- Common issues:
  - Missing libraries → Add them in Libraries folder
  - Java version mismatch → Check project properties
  - Compilation errors → Review error messages

---

## Step 5: Run the Application

### Method 1: Using Run Button
1. Right-click on **Panelara_Exportation_1** in the Projects panel
2. Select **Run** (or press `F6`)

### Method 2: Run Specific File
1. Expand **Source Packages → panelera_exportation.View**
2. Right-click on **Singin_panelera.java**
3. Select **Run File** (or press `Shift+F6`)

### Method 3: Using Main Toolbar
1. Make sure **Panelara_Exportation_1** is selected in Projects panel
2. Click the green **Run Project** button (▶) in the toolbar
3. Or press `F6`

---

## Step 6: Application Startup

When the application runs successfully, you should see:

### In the Output Window:
```
run:
Conectado                           # Database connected
Configuration loaded from config.properties
```

### Login Window Appears:
- **Title**: Login form (Singin_panelera)
- **Fields**: Username and Password
- **Buttons**: Login, Create User, etc.

---

## Testing the Application

### Test Login
1. **Using existing user** (if you have data in database):
   - Username: (your existing username)
   - Password: (your existing password)
   - Click **Login**

2. **Create new user first**:
   - Click **Create User** button
   - Fill in all fields:
     - Full Name
     - Email
     - Phone Number
     - Address
     - Username
     - Password
     - Birth Date
   - Click **Save/Register**
   - Return to login and use those credentials

### Test Order Management
After successful login:
1. Create a new order
2. View orders list
3. Edit an existing order
4. Delete an order

---

## Common Issues and Solutions

### Issue 1: "Cannot find config.properties"
**Solution:**
- Verify `config.properties` exists in project root
- Check Output window for the exact path NetBeans is looking for
- Make sure file is not in `/src/` folder (should be in root)

### Issue 2: "Cannot connect to database"
**Symptoms:** Error message about connection failure

**Solutions:**
1. Verify MySQL is running:
   ```bash
   mysql -u root -p -h localhost -P 3308
   ```

2. Check database exists:
   ```sql
   SHOW DATABASES LIKE 'panelera%';
   ```

3. Create database if missing:
   ```sql
   CREATE DATABASE panelera_exportation_1;
   SOURCE /Users/mac/NetBeansProjects/Panelara_Exportation_1/Panelera_exportation.sql;
   ```

4. Verify credentials in `config.properties`

5. Check firewall/port access

### Issue 3: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution:**
- Add `mysql-connector-java-8.0.20.jar` to Libraries
- Right-click Libraries → Add JAR/Folder
- Navigate to: `/Users/mysql-connector-java-8.0.20.jar`

### Issue 4: "Main class not found"
**Solution:**
1. Right-click project → **Properties**
2. Select **Run** category
3. Set Main Class: `panelera_exportation.View.Singin_panelera`
4. Click **OK**
5. Clean and Build again

### Issue 5: Build fails with compilation errors
**Solution:**
1. Check Output window for specific errors
2. Common fixes:
   - Update Java version: Right-click project → Properties → Sources → Source/Binary Format
   - Reload libraries: Right-click Libraries → Resolve Problems
   - Clean IDE cache: NetBeans → Cache → Clean Cache and Restart

### Issue 6: Window appears but crashes immediately
**Check Output window for:**
- Database connection errors → Fix database configuration
- Encryption errors → Verify `Endcoder.java` and `ConfigLoader.java` are present
- Null pointer exceptions → Check database has tables and data

---

## Running from Command Line (Alternative)

If you prefer to run outside NetBeans:

```bash
# Navigate to project directory
cd /Users/mac/NetBeansProjects/Panelara_Exportation_1

# Build the project
ant clean jar

# Run the JAR file
java -jar dist/Panelara_Exportation_1.jar

# Or with security policy
java -Djava.security.policy=applet.policy -jar dist/Panelara_Exportation_1.jar
```

---

## Debug Mode

To run in debug mode for troubleshooting:

1. Right-click project → **Debug** (or press `Ctrl+F5`)
2. Set breakpoints by clicking line numbers in code editor
3. Use debug toolbar to step through code:
   - **Step Over** (F8) - Execute current line
   - **Step Into** (F7) - Enter method calls
   - **Continue** (F5) - Run to next breakpoint
   - **Step Out** (Ctrl+F7) - Exit current method

---

## Performance Tips

1. **First Run**: May be slower as NetBeans indexes project
2. **Subsequent Runs**: Should be faster
3. **Clean Build**: Only needed when changing project structure
4. **Regular Run**: Usually sufficient for code changes

---

## Stopping the Application

- Close the application window normally (X button)
- Or in NetBeans Output window, click the red **Stop** button (⬛)
- Or press `Shift+F5`

---

## Quick Reference

| Action | Keyboard Shortcut | Menu Location |
|--------|------------------|---------------|
| Open Project | `Cmd+Shift+O` | File → Open Project |
| Clean and Build | `Shift+F11` | Right-click project → Clean and Build |
| Run Project | `F6` | Right-click project → Run |
| Debug Project | `Ctrl+F5` | Right-click project → Debug |
| Stop Application | `Shift+F5` | Output window → Stop button |
| Run File | `Shift+F6` | Right-click file → Run File |

---

## Next Steps After Running

1. ✅ Test all functionality
2. ✅ Change encryption key in `config.properties` to a secure value
3. ✅ Backup your database regularly
4. ✅ Review `IMPROVEMENTS.md` for security recommendations

---

## Need More Help?

- 📖 Check `IMPROVEMENTS.md` for detailed code changes
- 📖 Check `PROJECT_RENAME_SUMMARY.md` for rename details
- 🔍 Review NetBeans documentation: https://netbeans.apache.org/kb/
- 🐛 Check Output window for detailed error messages

---

**Last Updated**: October 31, 2025
**Project**: Panelara_Exportation_1
**Main Class**: `panelera_exportation.View.Singin_panelera`
