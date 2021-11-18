CREATE USER rtauserusage;
ALTER USER rtauserusage with PASSWORD '123';
GRANT USAGE ON SCHEMA rtaliqui TO rtauserusage WITH GRANT OPTION;
GRANT update, select, insert ON rtaliqui.rta to rtauserusage;
GRANT update, select, insert ON rtaliqui.address to rtauserusage;
GRANT update, select, insert ON rtaliqui.logs to rtauserusage;