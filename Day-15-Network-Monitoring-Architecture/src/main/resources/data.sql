INSERT INTO devices (hostname, ip_address, category, cpu_usage, memory_usage, uptime, status, temperature, interface_status, last_updated) VALUES
('Router-01', '10.15.0.11', 'ROUTER', 40.0, 35.0, 124500, 'ONLINE', 42.0, 'UP', CURRENT_TIMESTAMP),
('Router-02', '10.15.0.12', 'ROUTER', 88.0, 41.0, 87650, 'ONLINE', 55.0, 'UP', CURRENT_TIMESTAMP),
('Switch-01', '10.15.0.21', 'SWITCH', 42.0, 36.0, 222200, 'ONLINE', 39.0, 'UP', CURRENT_TIMESTAMP),
('Switch-02', '10.15.0.22', 'SWITCH', 2.0, 2.0, 19800, 'OFFLINE', 33.0, 'DOWN', CURRENT_TIMESTAMP),
('Modem-01', '10.15.0.31', 'MODEM', 44.0, 91.0, 64210, 'ONLINE', 48.0, 'UP', CURRENT_TIMESTAMP),
('Hub-01', '10.15.0.41', 'HUB', 41.0, 38.0, 54210, 'ONLINE', 43.0, 'UP', CURRENT_TIMESTAMP),
('BUC-01', '10.15.0.51', 'BUC', 44.0, 34.0, 73210, 'ONLINE', 46.0, 'UP', CURRENT_TIMESTAMP),
('LNB-01', '10.15.0.61', 'LNB', 39.0, 33.0, 53210, 'ONLINE', 40.0, 'UP', CURRENT_TIMESTAMP);
