/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {AppContext} from '../../../../src/main/resources/META-INF/resources/js/AppContext.es';
import UpperToolbar from '../../../../src/main/resources/META-INF/resources/js/components/upper-toolbar/UpperToolbar.es';

describe('UpperToolbar', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('render UpperToolbar with Childrens', async () => {
		const handleSearch = jest.fn();
		const query = {
			appDeploymentType: 'standalone-app',
		};
		const {container, queryByPlaceholderText, queryByRole} = render(
			<AppContext.Provider value={query}>
				<UpperToolbar>
					<UpperToolbar.Input placeholder="first..." />
					<UpperToolbar.Input
						onChange={() => {}}
						placeholder="last..."
					/>
					<UpperToolbar.Group>
						<UpperToolbar.Button onClick={handleSearch}>
							Search
						</UpperToolbar.Button>
					</UpperToolbar.Group>
				</UpperToolbar>
			</AppContext.Provider>
		);

		const first = queryByPlaceholderText('first...');
		const last = queryByPlaceholderText('last...');
		const button = queryByRole('button');

		expect(container.querySelector('.standalone-app')).toBeTruthy();
		expect(button).toBeTruthy();
		expect(first.value).toBe('');
		expect(last.value).toBe('');

		await act(async () => {
			fireEvent.change(first, {target: {value: 'first'}});
		});

		await act(async () => {
			fireEvent.change(last, {target: {value: 'last'}});
		});

		expect(first.value).toBe('first');
		expect(last.value).toBe('last');

		await act(async () => {
			button.dispatchEvent(new MouseEvent('click', {bubbles: true}));
		});

		expect(handleSearch.mock.calls.length).toBe(1);
	});
});
