-- Insert Users with hashed passwords (password is 'password')
INSERT INTO usr (id, email, name, picture, active, roles, password) VALUES
(1, 'tbeerbower@yahoo.com', 'Tom Beerbower', '', true, 'ROLE_ADMIN', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS'),
(2, 'jane.smith@example.com', 'Jane Smith', '', true, 'ROLE_USER', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS'),
(3, 'bob.wilson@example.com', 'Bob Wilson', '', true, 'ROLE_USER', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS'),
(4, 'alice.jones@example.com', 'Alice Jones', '', true, 'ROLE_USER', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS'),
(5, 'john.doe@example.com', 'John Doe', '', true, 'ROLE_USER', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS'),
(6, 'kate@example.com', 'Kate Spate', '', true, 'ROLE_USER', '$2a$10$TXkorQjx0GhjdjgJ2A84.OQ5W3Q5OWWu.SXXCKjyDt.vXD2WdzxyS');

SELECT setval('usr_id_seq', (SELECT MAX(id) FROM usr));

-- Predefined tags for van life locations
INSERT INTO tag (name, type, predefined, description) VALUES
('overnight_parking', 2, true, 'Suitable for overnight stays'),
('restaurant', 2, true, 'Places to eat'),
('landmark', 1, true, 'Interesting landmarks'),
('scenic_view', 1, true, 'Beautiful scenic views'),
('gas_station', 2, true, 'Places to refuel'),
('campground', 2, true, 'Designated camping areas'),
('wifi', 2, true, 'Locations with Wi-Fi access'),
('showers', 2, true, 'Locations with shower facilities'),
('hiking', 1, true, 'Great spots for hiking'),
('pet_friendly', 2, true, 'Pet-friendly locations');


-- Insert into location table
INSERT INTO location (latitude, longitude, popularity, safety_rating, user_id, created_at, updated_at, description, accessibility, address, cost, map_service_id, map_url, name)
VALUES
(40.0516, -76.3057, 8, 4.5, 1, '2025-02-09 08:00:00', '2025-02-09 08:00:00', 'A popular rest stop with clean facilities and food options.', 'Wheelchair accessible', 'Milepost 286, PA Turnpike', 'Free', '12345', 'https://maps.example.com/1', 'Lancaster Travel Plaza'),
(40.2415, -76.9245, 7, 4.2, 1, '2025-02-09 08:00:00', '2025-02-09 08:00:00', 'Gas station and fast food near the turnpike.', 'Limited accessibility', 'Milepost 242, PA Turnpike', 'Varies', '12346', 'https://maps.example.com/2', 'Harrisburg East Service Plaza'),
(40.3224, -78.9225, 9, 4.7, 1, '2025-02-09 08:00:00', '2025-02-09 08:00:00', 'Scenic view overlooking the mountains.', 'Fully accessible', 'Milepost 146, PA Turnpike', 'Free', '12347', 'https://maps.example.com/3', 'Allegheny Mountain Overlook'),
(40.6765, -80.1145, 7, 4.0, 1, '2025-02-09 08:00:00', '2025-02-09 08:00:00', 'Last rest stop before Ohio border.', 'Wheelchair accessible', 'Milepost 2, PA Turnpike', 'Free', '12444', 'https://maps.example.com/100', 'Gateway Plaza'),
(40.3211, -76.4708, 85, 4.5, 1, '2025-02-09 08:00:00', '2025-02-09 08:00:00', 'Full-service rest area with food, fuel, and restrooms.', 'Wheelchair accessible', 'Mile 266, Pennsylvania Turnpike', 'Free', 'map1236', 'https://maps.example.com/1236', 'Middletown Rest Stop'),
(40.0509, -76.3056, 72, 4.2, 1, '2025-02-09 08:10:00', '2025-02-09 08:10:00', 'Popular diner serving 24/7 breakfast and classic American meals.', 'Wheelchair accessible', '123 Lincoln Highway, Lancaster, PA', '$$', 'map1237', 'https://maps.example.com/1237', 'Lancaster Diner'),
(40.2173, -76.9157, 65, 4.0, 1, '2025-02-09 08:15:00', '2025-02-09 08:15:00', 'Scenic overlook with picnic tables and parking.', 'Limited accessibility', 'Mile 242, Pennsylvania Turnpike', 'Free', 'map1238', 'https://maps.example.com/1238', 'Susquehanna Scenic View'),
(39.9784, -76.6773, 90, 4.7, 1, '2025-02-09 08:20:00', '2025-02-09 08:20:00', 'High-rated gas station with EV charging, clean restrooms, and a café.', 'Wheelchair accessible', '456 Turnpike Plaza, York, PA', '$', 'map1239', 'https://maps.example.com/1239', 'York Fuel & EV Station'),
(40.4653, -78.5897, 55, 3.8, 1, '2025-02-09 08:25:00', '2025-02-09 08:25:00', 'Small campground with fire pits and basic amenities.', 'Not wheelchair accessible', 'Mile 195, Pennsylvania Turnpike', '$$', 'map1240', 'https://maps.example.com/1240', 'Turnpike Campground'),
(40.2672, -77.0289, 77, 4.3, 1, '2025-02-09 08:30:00', '2025-02-09 08:30:00', 'Popular truck stop with large parking area and showers.', 'Wheelchair accessible', '789 Route 15, Mechanicsburg, PA', '$$', 'map1241', 'https://maps.example.com/1241', 'Mechanicsburg Truck Stop'),
(40.6342, -75.4719, 62, 4.1, 1, '2025-02-09 08:35:00', '2025-02-09 08:35:00', 'Landmark diner with historic charm.', 'Wheelchair accessible', '100 Main St, Allentown, PA', '$$', 'map1242', 'https://maps.example.com/1242', 'Allentown Historic Diner'),
(40.7850, -76.1293, 88, 4.6, 1, '2025-02-09 08:40:00', '2025-02-09 08:40:00', 'Well-maintained rest stop with vending machines and pet area.', 'Wheelchair accessible', 'Mile 150, Pennsylvania Turnpike', 'Free', 'map1243', 'https://maps.example.com/1243', 'Schuylkill Rest Area'),
(40.9123, -75.4112, 80, 4.4, 1, '2025-02-09 08:45:00', '2025-02-09 08:45:00', 'Scenic hiking trail with moderate difficulty.', 'Not wheelchair accessible', 'Mile 125, Pennsylvania Turnpike', 'Free', 'map1244', 'https://maps.example.com/1244', 'Pocono Hiking Trail'),
(40.1106, -77.5205, 95, 4.8, 1, '2025-02-09 08:50:00', '2025-02-09 08:50:00', 'Premium gas station with attached convenience store.', 'Wheelchair accessible', 'Mile 190, Pennsylvania Turnpike', '$', 'map1245', 'https://maps.example.com/1245', 'Carlisle Fuel & Market'),
(40.0627, -76.3321, 8, 4.5, 1, '2024-02-09 08:15:00', '2024-02-09 08:15:00', 'Popular rest stop with food and fuel.', 'Accessible parking and restrooms', 'Mile Marker 286, PA Turnpike, Lancaster, PA', 'Free', 'mm286_rest', 'https://maps.example.com/mm286', 'Lancaster Travel Plaza'),
(40.1848, -76.7281, 7, 4.2, 1, '2024-02-09 09:00:00', '2024-02-09 09:00:00', 'Historic diner with homemade meals.', 'Wheelchair accessible', '123 Diner Rd, Harrisburg, PA', '$$', 'harrisburg_diner', 'https://maps.example.com/harrisburg_diner', 'Harrisburg Diner'),
(40.8437, -77.8948, 9, 4.8, 1, '2024-02-09 09:45:00', '2024-02-09 09:45:00', 'Scenic overlook with picnic areas.', 'Some steps to main viewpoint', 'Overlook Rd, State College, PA', 'Free', 'state_college_view', 'https://maps.example.com/state_college_view', 'State College Scenic Overlook'),
(40.3055, -76.8511, 6, 3.9, 1, '2024-02-09 10:30:00', '2024-02-09 10:30:00', 'Gas station with EV charging and Wi-Fi.', 'Fully accessible', '456 Fuel Ave, Camp Hill, PA', '$$', 'camp_hill_fuel', 'https://maps.example.com/camp_hill_fuel', 'Camp Hill Fuel & EV'),
(40.2581, -77.0052, 7, 4.3, 1, '2024-02-09 11:00:00', '2024-02-09 11:00:00', 'Quiet campground with showers and trails.', 'Uneven terrain in some areas', '789 Campground Rd, Carlisle, PA', '$$', 'carlisle_camp', 'https://maps.example.com/carlisle_camp', 'Carlisle Campground'),
(40.6914, -76.1780, 8, 4.4, 1, '2024-02-09 11:45:00', '2024-02-09 11:45:00', 'Truck stop with amenities for overnight stays.', 'Large vehicle parking available', '101 Truck Stop Ln, Pottsville, PA', '$', 'pottsville_truck', 'https://maps.example.com/pottsville_truck', 'Pottsville Truck Stop'),
(40.6300, -75.3700, 7, 4.1, 1, '2024-02-09 12:30:00', '2024-02-09 12:30:00', 'Classic diner with famous pancakes.', 'Wheelchair accessible', '555 Pancake St, Bethlehem, PA', '$$', 'bethlehem_diner', 'https://maps.example.com/bethlehem_diner', 'Bethlehem Pancake House'),
(40.6005, -75.4401, 8, 4.6, 1, '2024-02-09 13:15:00', '2024-02-09 13:15:00', 'Scenic rest area with hiking trails.', 'Accessible picnic tables', 'Mile Marker 55, PA Turnpike, Allentown, PA', 'Free', 'allentown_rest', 'https://maps.example.com/allentown_rest', 'Allentown Rest Area'),
(40.9202, -75.0970, 6, 3.7, 1, '2024-02-09 14:00:00', '2024-02-09 14:00:00', 'Hiking trail with stunning views.', 'Some steep inclines', 'Pocono Trails, Stroudsburg, PA', 'Free', 'pocono_trails', 'https://maps.example.com/pocono_trails', 'Pocono Hiking Trails'),
(40.2374, -77.1202, 7, 4.0, 1, '2024-02-09 14:45:00', '2024-02-09 14:45:00', 'Convenience store with fuel and groceries.', 'Fully accessible', '789 Market Rd, Shippensburg, PA', '$', 'shippensburg_market', 'https://maps.example.com/shippensburg_market', 'Shippensburg Fuel & Market');

-- Insert into location_tag table
INSERT INTO location_tag (location_id, tag_id)
VALUES
-- Lancaster Travel Plaza
(1, 2), (1, 5),
-- Harrisburg East Service Plaza
(2, 2), (2, 5),
-- Allegheny Mountain Overlook
(3, 4), (3, 9),
-- Gateway Plaza
(4, 1), (4, 2), (4, 5),
-- Middletown Rest Stop
(5, 1), (5, 2), (5, 5), (5, 7),
-- Lancaster Diner
(6, 2), (6, 10),
-- Susquehanna Scenic View
(7, 4), (7, 9), (7, 10),
-- York Fuel & EV Station
(8, 5), (8, 7),
-- Turnpike Campground
(9, 1), (9, 6), (9, 10),
-- Mechanicsburg Truck Stop
(10, 1), (10, 5), (10, 8), (10, 7),
-- Allentown Historic Diner
(11, 2), (11, 10),
-- Schuylkill Rest Area
(12, 1), (12, 5), (12, 7), (12, 10),
-- Pocono Hiking Trail
(13, 4), (13, 9), (13, 10),
-- Carlisle Fuel & Market
(14, 5), (14, 7),
-- Lancaster Travel Plaza (second entry)
(15, 1), (15, 2), (15, 5), (15, 7),
-- Harrisburg Diner
(16, 2), (16, 10),
-- State College Scenic Overlook
(17, 4), (17, 9),
-- Camp Hill Fuel & EV
(18, 5), (18, 7),
-- Carlisle Campground
(19, 1), (19, 6), (19, 8), (19, 9), (19, 10),
-- Pottsville Truck Stop
(20, 1), (20, 5), (20, 7), (20, 8),
-- Bethlehem Pancake House
(21, 2), (21, 10),
-- Allentown Rest Area
(22, 1), (22, 4), (22, 9),
-- Pocono Hiking Trails
(23, 4), (23, 9), (23, 10),
-- Shippensburg Fuel & Market
(24, 5), (24, 7);